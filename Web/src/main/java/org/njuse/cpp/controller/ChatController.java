package org.njuse.cpp.controller;


import org.njuse.cpp.bo.QuestionBO;
import org.njuse.cpp.bo.TestcaseBO;
import org.njuse.cpp.dao.ChatSessionMapper;
import org.njuse.cpp.dao.converter.TestcaseConverter;
import org.njuse.cpp.dao.po.TestcasePO;
import org.njuse.cpp.executor.DefaultExecutor;
import org.njuse.cpp.memory.BaseChatMessageHistory;
import org.njuse.cpp.memory.BaseMessage;
import org.njuse.cpp.request.GenerateRequest;
import org.njuse.cpp.service.QuestionService;
import org.njuse.cpp.service.SqlMemory;
import org.njuse.cpp.service.TestcaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/run")
public class ChatController {

    @Autowired
    QuestionService questionService;

    @Autowired
    TestcaseService testcaseService;

    @Autowired
    ChatSessionMapper chatSessionMapper;

    @PostMapping(value="/runWithOJ",produces = "text/event-stream")
    public SseEmitter runWithOJ(@RequestBody GenerateRequest request, HttpServletResponse response) {
        response.setHeader("Cache-Control","no-cache");
        response.setHeader("Connection","keep-alive");
        response.setHeader("X-Accel-Buffering","no");
        response.setCharacterEncoding("UTF-8");
        //TODO::前置校验

        SqlMemory sqlMemory = new SqlMemory(request.getSessionId(), request.getUserName(),
                request.getUserId(), chatSessionMapper);
        SseEmitter emitter = new SseEmitter(-1L);
        AtomicInteger index = new AtomicInteger(1);

        Disposable disposable = send(sqlMemory, request.getQuestionId(),request.getModelName())
                .doOnComplete(emitter::complete)
                .doOnEach(baseMessageSignal -> {
                    BaseMessage message = baseMessageSignal.get();
                    if(message==null){
                        emitter.complete();
                        return;
                    }
                    Map<String, Object> payload = new HashMap<>();
                    System.out.println(message.type());
                    if (message.type().equals("system")) {
                        payload.put("index", index.getAndIncrement());
                        payload.put("type", 2);
                        payload.put("result", message.getExtendParam("statusCode"));
                        payload.put("text", message.getContent());
                        payload.put("offset", 0);
                    }
                    else if(message.type().equals("human")){
                        payload.put("index", index.getAndIncrement());
                        payload.put("type", 0);
                        payload.put("result", -1);
                        payload.put("text", message.getContent());
                        payload.put("offset", 0);
                    }
                    else {
                        payload.put("index", index.getAndIncrement());
                        payload.put("type", 1);
                        payload.put("result", -1);
                        payload.put("text", message.getContent());
                        payload.put("offset", 0);
                    }
                    try {
                        emitter.send(payload);
//                        if (message.type().equals("system")){
//                            emitter.complete();
//                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        ).doOnError(emitter::completeWithError).subscribe();

        emitter.onTimeout(disposable::dispose);
        emitter.onCompletion(disposable::dispose);
        emitter.onError(e -> disposable.dispose());
        return emitter;
    }

    private Flux<BaseMessage> send(BaseChatMessageHistory memory, Long questionId,String modelName) {
        DefaultExecutor defaultExecutor = new DefaultExecutor();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        return Flux.create(emit -> {
            Map<String, Object> input = new HashMap<>();
            input.put("emit", emit);
            QuestionBO questionBO=questionService.getQuestionById(questionId);
            List<TestcasePO> testcasePOList=testcaseService.getAllTestcases(-1,0,questionId);
            List<TestcaseBO> testcaseBOS=testcasePOList.stream().map(TestcaseConverter::po2bo).collect(Collectors.toList());

            input.put("question", questionBO);
            input.put("testcases",testcaseBOS);
            input.put("modelName",modelName);
            input.put("memory", memory);

            executorService.submit(()->{
                defaultExecutor.run(input);
            });
        });
    }
}
