package com.inspur.ihealth.codes;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
public class OptionalTests {

    //https://www.jianshu.com/p/0bc2cbfeeee1
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(2);
        list.add(4);
        list.add(5);
        list.add(1);
        list.add(20);

        // 1. 选出list中的最小值
        Stream<Integer> streams = list.stream();
        Optional<Integer> minOptional = streams.min(Integer::compareTo);
        minOptional.ifPresent(min -> log.info("最小值-{}", min));
        streams.close();

        // 2. 选出最大值
        Stream<Integer> stream = list.stream();
        Optional<Integer> maxOptional = stream.max(Integer::compareTo);
        maxOptional.ifPresent(max -> log.info("最大值-{}", max));
        stream.close();

        // 3. 如果有值就返回，否则就返回一个标记
        Stream<Integer> stream2 = list.stream();
        Optional<Integer> existedOptional = stream2.filter(elem -> elem > 100)
                .max(Integer::compareTo);
        int result = existedOptional.orElse(-1000);
        log.info("过滤结果：{}", result);
        stream2.close();
    }


}
