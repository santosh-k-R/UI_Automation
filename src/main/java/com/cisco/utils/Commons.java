package com.cisco.utils;

import com.cisco.utils.auth.User;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.SkipException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Commons {

    public static Map<String, String> getTestData(String file, String sheet, Boolean multiline) {
        String[][] excelData = ExcelReader.readTestData(file, sheet, multiline);
        Map<String, String> data = new ConcurrentHashMap<>();
        if (multiline) {
            for (int i = 0; i < excelData[0].length; i++) {
                for (int j = 0; j < excelData.length; j++) {
                    data.put(excelData[0][i], excelData[j + 1][i]);
                }
            }
        } else {
            for (int i = 0; i < excelData[0].length; i++) {
                data.put(excelData[0][i], excelData[1][i]);
            }

        }
        return data;
    }

    public static byte[] compress(byte[] input, ByteArrayOutputStream jpegFiletoSave, float quality) throws IOException {
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(input));
        //Get the height and width of the image
        int width = image.getWidth();
        int height = image.getHeight();

        //Get the pixels of the image to an int array
        int[] pixels = image.getRGB(0, 0, width, height, null, 0, width);

        //Create a new buffered image without an alpha channel. (TYPE_INT_RGB)
        BufferedImage copy = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        //Set the pixels of the original image to the new image
        copy.setRGB(0, 0, width, height, pixels, 0, width);

        ImageWriter jpgWriter = ImageIO.getImageWritersByFormatName("jpg").next();
        ImageWriteParam jpgWriteParam = jpgWriter.getDefaultWriteParam();
        jpgWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        jpgWriteParam.setCompressionQuality(quality);

        jpgWriter.setOutput(ImageIO.createImageOutputStream(jpegFiletoSave));
        IIOImage outputImage = new IIOImage(copy, null, null);
        jpgWriter.write(null, outputImage, jpgWriteParam);
        jpgWriter.dispose();
        return jpegFiletoSave.toByteArray();
    }

    public static Response elasticSearchPost(String endPoint, String jsobObjectString) {
        String es_username = System.getProperty("es_username");
        String es_password = System.getProperty("es_password");

        Response response = null;
        RequestSpecification requestSpecification = RestAssured.given()
                .header("Authorization", "Basic " + encode(es_username + ":" + es_password))
                .contentType(ContentType.JSON)
                .body(jsobObjectString);
        try {
            response = requestSpecification.when().post(endPoint);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public static String encode(String actualValue) {
        Base64.Encoder encoder = Base64.getUrlEncoder();
        return encoder.encodeToString(actualValue.getBytes());
    }

    public static int generateRandomNumber() {
        Random random = new Random();
        return random.nextInt(999999);
    }

    public boolean executeCommand(String command) {
        String os = System.getProperty("os.name").toLowerCase();
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            if (os.contains("mac")) {
                processBuilder.command("bash", "-c", command);
            } else if (os.contains("windows")) {
                processBuilder.command("cmd.exe", "/c", command);
            }

            processBuilder.inheritIO();

            Process process = processBuilder.start();
            int exitVal = process.waitFor();
            if (exitVal == 0) {
                System.out.println("Success!");
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            return false;
        }
    }
    
    public static List<String> parseStringtoList(String colNames) {	
		List<String> colNameList = new ArrayList<String>();
		String[] arrCol=colNames.split(",");	
		for (int i=0;i<arrCol.length;i++) {
			colNameList.add(arrCol[i].trim());
		}		
		return colNameList;		
	}

    public static Map<String, String> constructParams(String params) {
        Map<String, String> parameters = new HashMap<>();
        String[] paramArray = params.split("&");
       /* if (paramArray.length % 2 != 0)
            throw new IllegalArgumentException("Either Key or Value missing in the parameters");*/

        for (String s : paramArray) {
            String[] temp = s.split("=");
            parameters.put(temp[0], temp[1]);
        }
        return parameters;
    }

    public static Map<String, String> constructHeader(String headers) {
        Map<String, String> headersMap = new HashMap<>();
        if (headers.contains("cx-context")) {
            int h = headers.indexOf("cx-context");
            int h1 = headers.indexOf("[", h);
            int v1 = headers.indexOf("]", h) + 1;
            headersMap.put("cx-context", headers.substring(h1, v1));
            headers = headers.replace(headers.substring(h, v1), "");
        }
        List<String> paramArray = Arrays.stream(headers.split(",")).filter(h -> !h.equals("")).collect(Collectors.toList());
        if (paramArray.size() % 2 != 0)
            throw new IllegalArgumentException("Either Key or Value missing in the headers");
        for (int i = 0; i < paramArray.size(); i = i + 2) {
            headersMap.put(paramArray.get(i), paramArray.get(i + 1));
        }
        return headersMap;
    }

    public static Map<String, String> constructCaseParams(String params) {
        Map<String, String> parameters = new HashMap<>();
        String[] paramArray = params.split(";");

        for (String param : paramArray)
            parameters.put(param.split("~")[0], param.split("~")[1]);
        return parameters;
    }

    public static Response getAPIResponse(String method, String url, String headers, String params, String body) {
        Response response = null;
        if (method.isEmpty() || url.isEmpty()) throw new SkipException("Method or URL cannot be blank");

        switch (method.toUpperCase()) {

            case "GET":
            case "HEAD":
                if (params.isEmpty() && headers.isEmpty())
                    response = RestUtils.get(url);
                else if (params.isEmpty())
                    response = RestUtils.get(url, constructHeader(headers));
                else if (headers.isEmpty())
                    response = RestUtils.get(url, params);
                else
                    response = RestUtils.get(url, headers, params);
                break;

            case "POST":
                body = body.isEmpty() ? "{}" : body;
                if (params.isEmpty() && headers.isEmpty())
                    response = RestUtils.post(url, body);
                else if (params.isEmpty())
                    response = RestUtils.post(url, body, constructHeader(headers));
                else if (headers.isEmpty())
                    response = RestUtils.post(url, body, params);
                else
                    response = RestUtils.post(url, body, headers, params);
                break;

            case "PUT":
                body = body.isEmpty() ? "{}" : body;
                if (params.isEmpty() && headers.isEmpty())
                    response = RestUtils.put(url, body);
                else if (params.isEmpty())
                    response = RestUtils.put(url, body, constructHeader(headers));
                else if (headers.isEmpty())
                    response = RestUtils.put(url, body, params);
                else
                    response = RestUtils.put(url, body, headers, params);
                break;
            case "POST_FORM":
                response = RestUtils.post(url, constructParams(params));
        }

        return response;
    }

    public static Response getAPIResponse(String method, String url, String headers, String params, String body, String userRole) {
        Response response = null;
        if (method.isEmpty() || url.isEmpty()) throw new SkipException("Method or URL cannot be blank");

        switch (method.toUpperCase()) {

            case "GET":
                if (params.isEmpty() && headers.isEmpty())
                    response = RestUtils.get(url, new User(userRole));
                else if (params.isEmpty())
                    response = RestUtils.get(url, constructHeader(headers), new User(userRole));
                else if (headers.isEmpty())
                    response = RestUtils.get(url, params, new User(userRole));
                else
                    response = RestUtils.get(url, headers, params, new User(userRole));
                break;

            case "POST":
                body = body.isEmpty() ? "{}" : body;
                if (params.isEmpty() && headers.isEmpty())
                    response = RestUtils.post(url, body, new User(userRole));
                else if (params.isEmpty())
                    response = RestUtils.post(url, body, constructHeader(headers), new User(userRole));
                else if (headers.isEmpty())
                    response = RestUtils.post(url, body, params, new User(userRole));
                else
                    response = RestUtils.post(url, body, headers, params, new User(userRole));
                break;

            case "PUT":
                body = body.isEmpty() ? "{}" : body;
                if (params.isEmpty() && headers.isEmpty())
                    response = RestUtils.put(url, body, new User(userRole));
                else if (params.isEmpty())
                    response = RestUtils.put(url, body, constructHeader(headers), new User(userRole));
                else if (headers.isEmpty())
                    response = RestUtils.put(url, body, params, new User(userRole));
                else
                    response = RestUtils.put(url, body, headers, params);
                break;

            case "DELETE":
                if (params.isEmpty() && headers.isEmpty())
                    response = RestUtils.delete(url, new User(userRole));
                else if (params.isEmpty())
                    response = RestUtils.delete(url, constructHeader(headers), new User(userRole));
                else if (headers.isEmpty())
                    response = RestUtils.delete(url, params, new User(userRole));
                else
                    response = RestUtils.delete(url, headers, params, new User(userRole));
                break;
        }
        return response;
    }



}


