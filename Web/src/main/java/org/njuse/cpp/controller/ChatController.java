package org.njuse.cpp.controller;


import org.njuse.cpp.executor.DefaultExecutor;
import org.njuse.cpp.memory.BaseChatMessageHistory;
import org.njuse.cpp.memory.BaseMessage;
import org.njuse.cpp.memory.SystemMessage;
import org.njuse.cpp.request.GenerateRequest;
import org.njuse.cpp.service.SqlMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/run")
public class ChatController {
    @Autowired
    SqlMemory sqlMemory;

    @PostMapping("/runWithOJ")
    public SseEmitter runWithOJ(@RequestBody GenerateRequest request) {
        //TODO::前置校验

        sqlMemory.init(request.getSessionId(), request.getUserName(), request.getUserId());
        SseEmitter emitter = new SseEmitter(-1L);
        AtomicInteger index = new AtomicInteger(1);

        Disposable disposable = send(sqlMemory, request.getQuestion()).doOnEach(baseMessageSignal -> {
                    BaseMessage message = baseMessageSignal.get();
                    Map<String, Object> payload = new HashMap<>();
                    if (message.type().equals("system")) {
                        payload.put("index", index.getAndIncrement());
                        payload.put("type", 2);
                        payload.put("result", message.getExtendParam("statusCode"));
                        payload.put("text", message.getContent());
                        payload.put("offset", 0);
                    }

                    try {
                        emitter.send(payload);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        ).doOnComplete(() -> {
            try {
                emitter.send("[DONE]");
            } catch (IOException e) {
                emitter.completeWithError(e);
            }
            emitter.complete();
        }).doOnError(emitter::completeWithError).subscribe();

        emitter.onTimeout(disposable::dispose);
        emitter.onCompletion(disposable::dispose);
        emitter.onError(e -> disposable.dispose());
        return emitter;
    }

    private Flux<BaseMessage> send(BaseChatMessageHistory memory, String question) {
        DefaultExecutor defaultExecutor = new DefaultExecutor();
        return Flux.create(emit -> {
            Map<String, Object> input = new HashMap<>();
            input.put("emit", emit);
            input.put("question", question);
            input.put("memory", memory);
            defaultExecutor.run(input);
        });
    }
}
