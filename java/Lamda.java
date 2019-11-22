
//


        log.info(mapper.writeValueAsString(result));

        result.forEach((key,value)->{
        log.info("key: " + key + "  value: " + value);
        });