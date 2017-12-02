package info.cukes.cucumber_jvm;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

//import com.nike.ocp.automation.framework.common.CommonConstant.RegexPatterns;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;

public class JsonUtil {
	 private static final Gson GSON;
	  private static final JsonParser PARSER;

	  static {
	    GSON = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd").create();
	    PARSER = new JsonParser();
	  }

	  private JsonUtil() {
	    // This class is designed to be used as a static utility
	  }

	  public static final String toJson(JsonElement jsonElement) {
	    return GSON.toJson(jsonElement);
	  }

	  public static final <T> T toObject(Type complexType, JsonElement jsonElement) {
	    return GSON.fromJson(jsonElement, complexType);
	  }

	  public static final <T> T toObject(Type complexType, String json) {
	    return GSON.fromJson(json, complexType);
	  }

	  public static final void writeJsonToFile(JsonElement json, String path) {
	    File file = new File(path);
	    if (!file.exists()) {
	      try {
	        file.createNewFile();
	      } catch (IOException e) {
	       e.printStackTrace();
	      }
	    }
	    try {
	      Files.write(toJson(json), file, Charset.forName("UTF8"));
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	  }

//	  public static final JsonObject createJsonFromConfiguration(final Configuration configuration) {
//
//	    Iterator<String> keyIter = configuration.getKeys();
//	    JsonObject jsonModel = new JsonObject();
//
//	    while (keyIter.hasNext()) {
//	      String key = keyIter.next();
//	      appendPropertyToJsonTemplateModel(jsonModel, key, configuration);
//	    }
//
//	    return jsonModel;
//
//	  }

//	  public static final JsonObject appendPropertyToJsonTemplateModel(JsonObject jsonModel, String key, Configuration configuration) {
//	    JsonElement finalValue = createValueElement(key, configuration);
//	    return resolvePath(jsonModel, key, finalValue).getAsJsonObject();
//	  }

	  public static final JsonElement toJson(Object object) {
	    if (object instanceof String) {
	      return parse((String) object);
	    }
	    return GSON.toJsonTree(object);
	  }

	  public static final JsonElement parseFromFile(String path) {
	    return parse(new File(path));
	  }

	  public static final <T> T toObject(Class<T> type, JsonElement json) {
	    return GSON.fromJson(json, type);
	  }

	  public static final <T> T toObject(Class<T> type, String json) {
	    return GSON.fromJson(json, type);
	  }

	  public static final JsonElement parse(String json) {
	    return PARSER.parse(json);
	  }

	  public static final JsonElement parse(File file) {
	    try {
	      return parse(new BufferedReader(new FileReader(file)));
	    } catch (FileNotFoundException e) {
	      throw new RuntimeException(e);
	    }
	  }

	  public static final JsonElement parse(InputStream inputStream) {
	    return parse(new BufferedReader(new InputStreamReader(inputStream)));
	  }

	  private static final JsonElement parse(BufferedReader bufferedReader) {

	    JsonElement element = null;
	    try {
	      element = PARSER.parse(bufferedReader);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    } finally {
	      try {
	        bufferedReader.close();
	      } catch (IOException e) {
	    	  e.printStackTrace();
	      }
	    }

	    return element;
	  }

	  private static final JsonElement resolvePath(final JsonObject root, String key, JsonElement finalValue) {
	    // treat all dots as a branch
	    String[] segments = key.split("\\.");
	    int segmentCount = segments.length;

	    JsonObject currentProperty = root;
	    String currentPath = "";

	    for (int i = 0; i < segmentCount; i++) {
	      String segment = segments[i];
	      boolean isLastSegment = 1 + i == segmentCount;
	      currentPath += segment;
	      if (isLastSegment) {
	        // properties are paths, not objects... so we can be safe
	        // knowing the final generated value is always the end of the
	        // property path.
	        currentProperty.add(segment, finalValue);
	      } else if (currentProperty.has(segment)) {
	        JsonElement nextProperty = currentProperty.get(segment);
	        if (nextProperty.isJsonObject()) {
	          currentPath += ".";
	          currentProperty = nextProperty.getAsJsonObject();
	        } else {
//	          LOGGER.debug("duplicate property detected: " + key + "     " + segment + "    " + currentProperty.toString());
//	          LOGGER.debug("moving " + currentProperty.toString() + " to " + segment + ".value");

	          JsonObject jsonObject = new JsonObject();
	          jsonObject.add("value", currentProperty.get(segment));
	          currentProperty.add(segment, jsonObject);

//	          LOGGER.debug("conflicting property name resolved to: " + currentProperty);
	          currentProperty = jsonObject;
	        }

	      } else {
//	        LOGGER.debug("Creating json namespace: " + currentPath);
	        currentPath += ".";
	        JsonObject nextProperty = new JsonObject();
	        currentProperty.getAsJsonObject().add(segment, nextProperty);
	        currentProperty = nextProperty;
	      }

	    }

	    return root;
	  }

//	  private static final JsonElement createValueElement(String key, Configuration configuration) {
//
//	    String value = configuration.getString(key);
//	    Object actualValue = configuration.getProperty(key);
//	    //Matcher floatMatcher = RegexPatterns.IS_FLOATING_NUMBER_PATTERN.matcher(value);
//	    //floatMatcher.find();
//
//	    Matcher intMatcher = RegexPatterns.IS_INTEGER_PATTERN.matcher(value);
//	    intMatcher.find();
//
//	    boolean isDecimal = floatMatcher.matches();
//	    boolean isInteger = intMatcher.matches();
//	    boolean isArray = List.class.isAssignableFrom(actualValue.getClass());
//
//	    if (isArray) {
//	      JsonArray jsonArray = new JsonArray();
//	      for (String arrayValue : configuration.getStringArray(key)) {
//	        jsonArray.add(new JsonPrimitive(arrayValue));
//	      }
//	      return jsonArray;
//	    }
//
//	    if (isInteger) {
//	      try {
//	        return new JsonPrimitive(configuration.getBigInteger(key));
//	      } catch (ConversionException e) {
//	        LOGGER.warn("Could not convert " + key + ": " + configuration.getProperty(key) + " to integer");
//	      }
//	    }
//
//	    if (isDecimal) {
//	      return new JsonPrimitive(configuration.getBigDecimal(key));
//	    }
//
//	    // boolean and other complex type mappings happen under the hood by GSON
//	    return new JsonPrimitive(value);
//	  }
//
//	  public static void writeJsonToFile(JsonElement jsonElement, File file) {
//	    writeJsonToFile(jsonElement, file.getAbsolutePath());
//	    LOGGER.info("wrote json contents to file " + file.toURI());
//	  }

	
	
}
