package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class FluxAndMonoGeneratorService {

    public Flux<String> namesFlux() {
        return Flux.fromIterable(List.of("alex", "ben", "kadu")).log(); // db or a remote service call
    }

    public Flux<String> namesFlux_map() {
        return Flux.fromIterable(List.of("alex", "ben", "kadu"))
                .map(String::toUpperCase)
                .log(); // db or a remote service call
    }

    public Flux<String> namesFlux_map(int stringLength) {
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .map(s -> s.length() + "-" + s)
                .log(); // db or a remote service call
    }

    public Flux<String> namesFlux_flatmap(int stringLength) {
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .flatMap(s -> splitName(s))
                .log(); // db or a remote service call
    }

    public Flux<String> namesFlux_flatmap_async(int stringLength) {
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .flatMap(s -> splitName_withDelay(s))
                .log(); // db or a remote service call
    }

    public Flux<String> namesFlux_concatMap(int stringLength) {
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .concatMap(s -> splitName_withDelay(s))
                .log(); // db or a remote service call
    }

    public Flux<String> namesFlux_immutability() {
        Flux<String> namesFlux = Flux.fromIterable(List.of("alex", "ben", "kadu"));
        namesFlux.map(String::toUpperCase);
        return namesFlux;
    }

    public Mono<String> nameMono() {
        return Mono.just("alex")
                .log();
    }

    public Mono<String> nameMono_map_filter(int stringLength) {
        return Mono.just("alex")
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .log();
    }
    public Mono<List<String>> nameMono_flatMap(int stringLength) {
        return Mono.just("alex")
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .flatMap(this::splitNameMono)
                .log();
    }

    public Flux<String> nameMono_flatMapMany(int stringLength) {
        return Mono.just("alex")
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .flatMapMany(this::splitName)
                .log();
    }

    public Flux<String> namesFlux_transform(int stringLength) {

        Function<Flux<String>, Flux<String>> filterMap = name -> name.map(String::toUpperCase)
                .filter(s -> s.length() > stringLength);

        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .transform(filterMap)
                .flatMap(s -> splitName(s))
                .defaultIfEmpty("default")
                .log();
    }

    public Flux<String> namesFlux_transform_switchIfEmpty(int stringLength) {

        Function<Flux<String>, Flux<String>> filterMap = name -> name.map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .flatMap(s -> splitName(s));

        Flux<String> defaultFlux = Flux.just("default").transform(filterMap);// "D", "E", "F", "A", "U", "L", "T"

        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .transform(filterMap)
                .flatMap(s -> splitName(s))
                .switchIfEmpty(defaultFlux)
                .log();
    }

    public Flux<String> explore_concat() {

        var abcFlux = Flux.just("A", "B", "C");

        var defFlux = Flux.just("D", "E", "F");

        return Flux.concat(abcFlux, defFlux).log();
    }

    public Flux<String> explore_concatWith() {

        var abcFlux = Flux.just("A", "B", "C");

        var defFlux = Flux.just("D", "E", "F");

        return abcFlux.concatWith(defFlux).log();
    }

    public Flux<String> explore_concatWith_mono() {

        var aMono = Mono.just("A");

        var bMono = Mono.just("B");

        return aMono.concatWith(bMono).log(); // A, B
    }

    public Flux<String> explore_concatWith_monoFlux() {

        var aMono = Mono.just("A");

        var defFlux = Flux.just("D", "E", "F");

        return aMono.concatWith(defFlux).log(); // A, D, E, F
    }

    public Flux<String> explore_merge() {

        var abcFlux = Flux.just("A", "B", "C")
                .delayElements(Duration.ofMillis(150));

        var defFlux = Flux.just("D", "E", "F")
                .delayElements(Duration.ofMillis(100));

        return Flux.merge(abcFlux, defFlux).log();
    }

    public Flux<String> explore_mergeWith() {

        var abcFlux = Flux.just("A", "B", "C")
                .delayElements(Duration.ofMillis(150));

        var defFlux = Flux.just("D", "E", "F")
                .delayElements(Duration.ofMillis(100));

        return abcFlux.mergeWith(defFlux).log();
    }

    public Flux<String> explore_mergeWith_mono() {

        var aMono = Mono.just("A");

        var bMono = Mono.just("B");

        return aMono.mergeWith(bMono).log(); // A, B
    }


    public Flux<String> explore_mergeSequential() {

        var abcFlux = Flux.just("A", "B", "C")
                .delayElements(Duration.ofMillis(150));

        var defFlux = Flux.just("D", "E", "F")
                .delayElements(Duration.ofMillis(100));

        return Flux.mergeSequential(abcFlux, defFlux).log();
    }

    public Flux<String> explore_zip() {

        var abcFlux = Flux.just("A", "B", "C");

        var defFlux = Flux.just("D", "E", "F");

        return Flux.zip(abcFlux, defFlux, (first, second) -> first + second)
                .log(); // AD, BE, CF
    }

    public Flux<String> explore_zip_1() {

        var abcFlux = Flux.just("A", "B", "C");

        var defFlux = Flux.just("D", "E", "F");

        var _123Flux = Flux.just("1", "2", "3");

        var _456Flux = Flux.just("4", "5", "6");

        return Flux.zip(abcFlux, defFlux, _123Flux, _456Flux)
                .map(t4 -> t4.getT1() + t4.getT2() + t4.getT3() + t4.getT4())
                .log(); // AD14, BE25, CF36
    }

    public Flux<String> explore_zipWith() {

        var abcFlux = Flux.just("A", "B", "C");

        var defFlux = Flux.just("D", "E", "F");

        return abcFlux.zipWith(defFlux, (first, second) -> first + second)
                .log(); // AD, BE, CF
    }

    public Mono<String> explore_zipWith_mono() {

        var aMono = Mono.just("A");

        var bMono = Mono.just("B");

        return aMono.zipWith(bMono)
                .map(t2 -> t2.getT1() + t2.getT2())
                .log(); // AB
    }

    private Flux<String> splitName_withDelay(String name) {
        var charArray = name.split("");
        var delay = new Random().nextInt(2000);
        return Flux.fromArray(charArray).delayElements(Duration.ofMillis(delay));
    }

    private Flux<String> splitName(String name) {
        var charArray = name.split("");
        return Flux.fromArray(charArray);
    }

    private Mono<List<String>> splitNameMono(String name) {
        var charArray = name.split("");
        var charList = List.of(charArray);
        return Mono.just(charList);
    }

    public static void main(String[] args) {
        FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();
        fluxAndMonoGeneratorService.namesFlux().subscribe(name -> {
            System.out.println("Name is : " + name);
        });

        fluxAndMonoGeneratorService.nameMono().subscribe(name -> {
            System.out.println("Mono name is : " + name);
        });
    }
}
