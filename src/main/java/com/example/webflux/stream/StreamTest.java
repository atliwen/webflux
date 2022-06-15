package com.example.webflux.stream;

import com.example.webflux.User;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Stream;

/**
 * @author 李文
 * @create 2022-05-30 13:55
 **/
public class StreamTest
{


    public static void main(String[] args) {

        Mono<String> mono = WebClient
                //创建WenClient实例
                .create()
                //方法调用，WebClient中提供了多种方法
                .method(HttpMethod.GET)
                //请求url
                .uri("https://www.jianshu112.com/p/cc3a99614476")
                //获取响应结果
                .retrieve()
                //将结果转换为指定类型
                .bodyToMono(String.class)
                .onErrorResume(c->{
                    System.out.println(c);
                    return  Mono.just("aa");
                });
        //block方法返回最终调用结果，block方法是阻塞的
        System.out.println("响应结果：" + mono.block());
    }

    // K url V  mono
    public Map<String, Mono<Object>> monoMap = new HashMap<>();


    public static void main1(String[] args) {
        //flatMap  转为一
        Mono.just(new User("aa")).flatMap(c->  Mono.just("aa"))
                .subscribe();
        //flatMapMany 可以转化为多个
        Mono.just(new User("aa")).flatMapMany(user -> Flux.just(new User("aa")))
                .subscribe();


        // 所有请求都转换为 k v  字符串形式
        Map<String, String> map = new HashMap<>();
        map.put("a", "testA");
        map.put("b", "testA");
        Flux.just(map).flatMap(c -> Mono.just(new User("aa"))).subscribe();


        // 示例   mono 转换为 flux  添加一个 mono 转换为flux
        Mono.just(map).concatWith(Mono.just(map))
                .doOnNext(c -> System.out.println(c)).subscribe();

        System.out.println("-*---------*-*-*");
        // 创建消息数据
        Mono.just(map)
                // 构建一次执行数据（无返回值）
                .doOnNext(cd -> {
                    cd.put("c", "testc"); //   可通过添加 map 初始消息的方式传递参数 约定名称
                    System.out.println(cd.get("a"));
                }).doOnSuccess(c -> {
                })
                // 进行数据转换   可以返回一个新的数据类型的数据
                .flatMap(d -> {
                    return Mono.just(new User("dddd"));
                })
                // 对转换后的类做处理，并且模拟出现异常情况
                .doOnNext(cd -> {
                    cd.setAge(18);
                    throw new NullPointerException("我抛出一个空异常了1");
                })
                // 记录错误日志 不能处理异常数据 （无回退）  处理异常方式 一
                .doOnError(c -> {
                    System.out.println(c.getMessage());
                })
                // 可以做为异常回退方法  处理异常方式 二
                .onErrorResume(c -> {
                    System.out.println(c.getMessage());
                    // 模拟在异常回退方法中继续抛出异常
                    if (1 == 1) {
                        throw new NullPointerException("我抛出一个空异常了2");
                    }
                    return Mono.just(new User("testUser"));
                })
                //  回退方法的返回值可作为继续处理的数据。
                .doOnNext(c -> System.out.println(c.getName()))
                .subscribe(System.out::println);


        //
        //Mono.just(c).doOnNext(ca -> ca.put("aaa", new User("na"))).map(s ->
        //
        //).doOnNext(System.out::println).subscribe();


        //
        //Flux.just(u).doOnNext(c -> {
        //    //System.out.println(c.getName());
        //}).doOnNext(c -> {
        //    System.out.println(c.getGender());
        //}).collect(Collectors.toList()).subscribe();

    }


    //public Mono<ServerResponse> getAllUsers(ServerRequest serverRequest) {
    //    return ok().contentType(APPLICATION_JSON)
    //            .body(userRepository.findAll(), User.class);
    //}
    static Mono<ServerResponse> findUserById(ServerRequest c) {

        List<User> list = new ArrayList<>();
        User u = new User();
        u.setName("A");
        u.setGender(c.pathVariable("id"));
        list.add(u);
        return ServerResponse.status(HttpStatus.OK).bodyValue(list);
    }

    private static void Stream() {
        // 1. Individual values
        Stream stream = Stream.of("a", "b", "c");

        // 2. Arrays
        String[] strArray = new String[]{"a", "b", "c"};
        stream = Stream.of(strArray);
        stream = Arrays.stream(strArray);

        // 3. Collections
        List<String> list = Arrays.asList(strArray);
        stream = list.stream();
    }
}
