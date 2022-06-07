package com.example.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@SpringBootApplication
public class WebfluxApplication
{

    public static void main(String[] args) {
        SpringApplication.run(WebfluxApplication.class, args);
    }

    @Bean
    public RouterFunction<ServerResponse> initRouterFunction() {
        return RouterFunctions.route()
                .GET("/hello/{name}", c -> findUserById(c)
                ).build();
    }

    static Mono<ServerResponse> findUserById(ServerRequest c) {


        Mono mono = Mono.just(c).doOnNext(cd -> {
            System.out.println(cd.getClass());
        });

        return ok().body(mono, User.class);

    }
    //
    //public Mono<ServerResponse> getAllUsers(ServerRequest serverRequest) {
    //    List<User> users=new ArrayList<>();;
    //    User u=new User();
    //    u.setName("A");
    //    users.add(u);
    //    Flux.just(users);
    //
    //    return ok().contentType(APPLICATION_JSON)
    //            .body(userRepository.findAll(), User.class);
    //}


//    //根据 id 查询
//    public Mono<ServerResponse> getUserById(ServerRequest request) {
//        //获取 id 值
//        int userId = Integer.valueOf(request.pathVariable("id"));
//        //空值处理
//        Mono<ServerResponse> notFound = ServerResponse.notFound().build();
//        //调用 service 方法得到数据
//        Mono<User> userMono = this.userService.getUserById(userId);
//        //把 userMono 进行转换返回
//        //使用 Reactor 操作符 flatMap
//        return userMono.flatMap(person -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(fromObject(person))).switchIfEmpty(notFound);
//    }
}
