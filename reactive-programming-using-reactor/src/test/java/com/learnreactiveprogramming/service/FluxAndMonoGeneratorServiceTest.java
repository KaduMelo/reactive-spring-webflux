package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

public class FluxAndMonoGeneratorServiceTest {

    FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();

    @Test
    void namesFlux() {
        // given

        // when
        var namesFlux = fluxAndMonoGeneratorService.namesFlux();

        // then
        StepVerifier.create(namesFlux)
                .expectNext("alex")
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void namesFlux_map() {
        // given

        // when
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_map();

        // then
        StepVerifier.create(namesFlux)
                .expectNext("ALEX", "BEN", "KADU")
                .verifyComplete();
    }

    @Test
    void namesFlux_immutability() {
        // given

        // when
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_immutability();

        // then
        StepVerifier.create(namesFlux)
                .expectNext("alex", "ben", "kadu")
                .verifyComplete();
    }

    @Test
    void nameMono() {
        // given

        // when
        var nameMono = fluxAndMonoGeneratorService.nameMono();

        // then
        StepVerifier.create(nameMono)
                .expectNext("alex")
                .verifyComplete();
    }

    @Test
    void namesFluxStringLength_map() {
        // given
        int stringLength = 3;

        // when
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_map(stringLength);

        // then
        StepVerifier.create(namesFlux)
                .expectNext("4-ALEX", "5-CHLOE")
                .verifyComplete();
    }

    @Test
    void namesFlux_flatmap() {
        // given
        int stringLength = 3;

        // when
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_flatmap(stringLength);

        // then
        StepVerifier.create(namesFlux)
                .expectNext("A","L","E","X","C","H","L","O","E")
                .verifyComplete();
    }

    @Test
    void namesFlux_flatmap_async() {
        // given
        int stringLength = 3;

        // when
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_flatmap_async(stringLength);

        // then
        StepVerifier.create(namesFlux)
                .expectNextCount(9)
                .verifyComplete();
    }

    @Test
    void namesFlux_concatmap() {
        // given
        int stringLength = 3;

        // when
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_concatMap(stringLength);

        // then
        StepVerifier.create(namesFlux)
                .expectNext("A","L","E","X","C","H","L","O","E")
                .verifyComplete();
    }

    @Test
    void nameMono_map_filter() {
        // given
        int stringLength = 3;

        // when
        var nameMono = fluxAndMonoGeneratorService.nameMono_map_filter(stringLength);

        // then
        StepVerifier.create(nameMono)
                .expectNext("ALEX")
                .verifyComplete();
    }

    @Test
    void nameMono_flatmap() {
        // given
        int stringLength = 3;

        // when
        var nameMono = fluxAndMonoGeneratorService.nameMono_flatMap(stringLength);

        // then
        StepVerifier.create(nameMono)
                .expectNext(List.of("A","L","E","X"))
                .verifyComplete();
    }

    @Test
    void nameMono_flatMapMany() {
        // given
        int stringLength = 3;

        // when
        var nameMono = fluxAndMonoGeneratorService.nameMono_flatMapMany(stringLength);

        // then
        StepVerifier.create(nameMono)
                .expectNext("A","L","E","X")
                .verifyComplete();
    }

    @Test
    void namesFlux_transform() {
        // given
        int stringLength = 3;

        // when
        var nameMono = fluxAndMonoGeneratorService.namesFlux_transform(stringLength);

        // then
        StepVerifier.create(nameMono)
                .expectNext("A","L","E","X","C","H","L","O","E")
                .verifyComplete();
    }

    @Test
    void namesFlux_transform_defaultOption() {
        // given
        int stringLength = 6;

        // when
        var nameMono = fluxAndMonoGeneratorService.namesFlux_transform(stringLength);

        // then
        StepVerifier.create(nameMono)
                .expectNext("default")
                .verifyComplete();
    }

    @Test
    void namesFlux_transform_switchIfEmpty() {
        // given
        int stringLength = 6;

        // when
        var nameMono = fluxAndMonoGeneratorService.namesFlux_transform_switchIfEmpty(stringLength);

        // then
        StepVerifier.create(nameMono)
                .expectNext("D", "E", "F", "A", "U", "L", "T")
                .verifyComplete();
    }

    @Test
    void explore_concat() {
        // given

        // when
        var concatFlux = fluxAndMonoGeneratorService.explore_concat();

        // then
        StepVerifier.create(concatFlux)
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();
    }

    @Test
    void explore_concatWith() {
        // given

        // when
        var concatFlux = fluxAndMonoGeneratorService.explore_concatWith();

        // then
        StepVerifier.create(concatFlux)
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();
    }

    @Test
    void explore_concatWith_mono() {
        // given

        // when
        var concatFlux = fluxAndMonoGeneratorService.explore_concatWith_mono();

        // then
        StepVerifier.create(concatFlux)
                .expectNext("A", "B")
                .verifyComplete();
    }

    @Test
    void explore_concatWith_monoFlux() {
        // given

        // when
        var concatFlux = fluxAndMonoGeneratorService.explore_concatWith_monoFlux();

        // then
        StepVerifier.create(concatFlux)
                .expectNext("A", "D", "E", "F")
                .verifyComplete();
    }

    @Test
    void explore_merge() {
        // given

        // when
        var concatFlux = fluxAndMonoGeneratorService.explore_merge();

        // then
        StepVerifier.create(concatFlux)
                .expectNextCount(6)
                .verifyComplete();
    }

    @Test
    void explore_mergeWith() {
        // given

        // when
        var concatFlux = fluxAndMonoGeneratorService.explore_mergeWith();

        // then
        StepVerifier.create(concatFlux)
                .expectNext("D", "A", "E", "B", "F", "C")
                .verifyComplete();
    }

    @Test
    void explore_mergeWithMono() {
        // given

        // when
        var concatFlux = fluxAndMonoGeneratorService.explore_mergeWith_mono();

        // then
        StepVerifier.create(concatFlux)
                .expectNext("A", "B")
                .verifyComplete();
    }

    @Test
    void explore_mergeSequential() {
        // given

        // when
        var value = fluxAndMonoGeneratorService.explore_mergeSequential();

        // then
        StepVerifier.create(value)
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();
    }

    @Test
    void explore_zip() {
        // given

        // when
        var value = fluxAndMonoGeneratorService.explore_zip();

        // then
        StepVerifier.create(value)
                .expectNext("AD", "BE", "CF")
                .verifyComplete();
    }

    @Test
    void explore_zip_1() {
        // given

        // when
        var value = fluxAndMonoGeneratorService.explore_zip_1();

        // then
        StepVerifier.create(value)
                .expectNext("AD14", "BE25", "CF36")
                .verifyComplete();
    }

    @Test
    void explore_zipWith() {
        // given

        // when
        var value = fluxAndMonoGeneratorService.explore_zipWith();

        // then
        StepVerifier.create(value)
                .expectNext("AD", "BE", "CF")
                .verifyComplete();
    }

    @Test
    void explore_zipWith_mono() {
        // given

        // when
        var value = fluxAndMonoGeneratorService.explore_zipWith_mono();

        // then
        StepVerifier.create(value)
                .expectNext("AB")
                .verifyComplete();
    }
}