//package com.example.webflux;
//
//import org.springframework.http.server.reactive.HttpHandler;
//import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
//import org.springframework.web.reactive.function.server.RouterFunction;
//import org.springframework.web.reactive.function.server.RouterFunctions;
//import org.springframework.web.reactive.function.server.ServerResponse;
//import reactor.netty.http.server.HttpServer;
//
//public class Service {
//    //1 创建 Router 路由
//    public RouterFunction<ServerResponse> routingFunction() {
//        //创建 hanler 对象
//
//        //设置路由
//        RouterFunctions.route().GET("/users/{id}").and(accept(APPLICATION_JSON)),handler::getUserById)
//        return RouterFunctions.route(
//                GET("/users/{id}").and(accept(APPLICATION_JSON)),handler::getUserById)
//                .andRoute(GET("/users").and(accept(APPLICATION_JSON)),handler::getAllUsers);
//    }
//    //2 创建服务器完成适配
//    public void createReactorServer() {
//        //路由和 handler 适配
//        RouterFunction<ServerResponse> route = routingFunction();
//        HttpHandler httpHandler = toHttpHandler(route);
//        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter( httpHandler);
//        //创建服务器
//        HttpServer httpServer = HttpServer.create();
//        httpServer.handle(adapter).bindNow();
//    }
//    public static void main(String[] args) throws Exception{
//        Service server = new Service();
//        server.createReactorServer();
//        System.out.println("enter to exit");
//        System.in.read();
//    }
//}
