package me.iolsh.homepage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class BaseTest {

    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

    public  String asJsonString(Object o) throws Exception {
        return ow.writeValueAsString(o);
    }


}
