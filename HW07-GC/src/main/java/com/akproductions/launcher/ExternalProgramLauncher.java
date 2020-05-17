package com.akproductions.launcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExternalProgramLauncher {
    private static Pattern gcMessage= Pattern.compile (".+\\[gc[\\s]+\\].+[m]*s$");

    public static void main(String[] args)
            throws IOException, InterruptedException {
        String jarFile="C:\\Users\\akuzmin\\Desktop\\java-2020-03\\toCheck\\java-2020-03\\HW07-GC\\build\\libs\\simpleApplication-0.1.jar";
        constructTestScenario(jarFile);
    }

    private static void constructTestScenario(String jarFile) throws IOException, InterruptedException {
        String jvmOptsBasic="-Xms256m -Xmx4192m -verbose:gc -XX:+PrintGCDetails";
        String[] testName = new String[5];
        testName [0]= "-XX:+UseSerialGC";
        testName [1]="-XX:+UseParallelGC";
        testName [2]="-XX:+UseConcMarkSweepGC";
        testName [3]="-XX:+UseG1GC";
        testName [4]="-XX:+UnlockExperimentalVMOptions -XX:+UseZGC";

        for (int t=0; t<testName.length;t++) {
            System.out.println("test 10 times GC for collector " + testName[t]);
            for (int i = 0; i < 10; i++) {
                String jvmOpts = jvmOptsBasic + " "+ testName[t];
                invokeJavaApplication(jarFile, jvmOpts);
            }
        }
    }

    private static void invokeJavaApplication(String jarFile, String jvmOpts) throws InterruptedException, IOException {
        List cmdParams= new  ArrayList(jvmOpts.split(" ").length+3);
        cmdParams.add("java");
        for (String s : jvmOpts.split(" ")) {
            cmdParams.add(s);
        }
        cmdParams.add("-jar");
        cmdParams.add(jarFile);

        ProcessBuilder procBuilder = new ProcessBuilder(cmdParams);
        procBuilder.redirectErrorStream(true);
        long appTimeStart = System.currentTimeMillis();
        Process process = procBuilder.start();
        InputStream stdout = process.getInputStream();
        InputStreamReader isrStdout = new InputStreamReader(stdout);
        BufferedReader brStdout = new BufferedReader(isrStdout);


        List gcLogs = new ArrayList<String>(100);
        List appLogs = new ArrayList<String>(100);

        String line = null;
        while((line = brStdout.readLine()) != null) {
            if(gcMessage.matcher(line).find()) {gcLogs.add(line);}
            else {appLogs.add(line);}
        }

        int exitVal = process.waitFor();
        long appTimeFinish = System.currentTimeMillis();

        float timeToGC=0;
        for (Object gcLog : gcLogs) {
            Pattern pattern = Pattern.compile("\\s([\\d\\.]+)ms$");
            Matcher matcher = pattern.matcher((String)gcLog);
            while (matcher.find()) {
                timeToGC+=Float.parseFloat(matcher.group(1));
            }


        }
        System.out.println("app exit code is "+exitVal+".app time is "+(appTimeFinish-appTimeStart)/1000+"s. total GC time is " + timeToGC/1000 + " s" );
        if (exitVal!=0){
            System.out.println("see app log below:");
            appLogs.forEach(System.out::println);
            }


    }
}
