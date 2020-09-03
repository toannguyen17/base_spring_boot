package com.red.system.http;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StringTrimJackSON extends SimpleModule {
	public StringTrimJackSON() {
		addDeserializer(String.class, new StdScalarDeserializer<String>(String.class) {
			@Override
			public String deserialize(JsonParser jsonParser, DeserializationContext ctx) throws IOException {
				return jsonParser.getValueAsString().trim();
			}
		});
	}
}
