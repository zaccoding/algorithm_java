import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;

/**
 *
 */
public class SourceGenerateRunner {

    static final String BASIC = "chap/basic";
    static final String BRUTE_FORCE = BASIC + "/bruteforce";
    static final String DP = "chap08/dp";
    static final String BITMASK = "chap16/bitmask";
    static final String PARTIALSUM = "chap17/partialsum";
    static final String QUEUESTACKDEQUE = "chap19/questackdeque";
    static final String GRAPH = "chap27/graph";

    public static void main(String[] args) {
        try {
            args = new String[] {
                    GRAPH
                    , "11724"
            };

            if (args == null || args.length != 2) {
                printHelp();
                System.exit(1);
            }

            writeDefaultJavaFile(args);
        } catch (Exception e) {
            System.out.println("Failed to create file. reason : " + e.getMessage());
        }
    }

    private static void writeDefaultJavaFile(String[] args) throws Exception {
        try {
            Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            throw new Exception("Problem must be integer but \"" + args[1] + "\"");
        }
        String packageValue = args[0] + "/p" + args[1];
        System.out.println("## Try to create package " + packageValue);

        Path sourcePath = Paths.get("src");
        Path packagePath = sourcePath.resolve(packageValue);

        // create package
        Files.createDirectories(packagePath, new FileAttribute[0]);

        // read default java
        File defaultSourceFile = new File("defaultMainReader.txt");
        File writeSourceFile = packagePath.resolve("Try1.java").toFile();
        writeSourceFile.createNewFile();
        System.out.println("## Try to write Try1 java file : " + writeSourceFile.getAbsolutePath());

        try (FileInputStream fis = new FileInputStream(defaultSourceFile);
             FileOutputStream fos = new FileOutputStream(writeSourceFile)) {

            StringBuilder sb = new StringBuilder();
            sb.append("package ")
              .append(packageValue.replace("/", "."))
              .append(";\n\n");

            int availableLen = fis.available();
            byte[] buf = new byte[availableLen];
            fis.read(buf);

            sb.append(new String(buf));

            String gitComment = "https://www.acmicpc.net/problem/";
            String source = sb.toString()
                              .replace(
                                      gitComment,
                                      gitComment + args[1]
                              );

            fos.write(source.getBytes());
        }
    }

    private static void printHelp() {
        System.out.println("-------------------------------------------------");
        System.out.println("java CommandLineRunner [package] [problem]");
        System.out.println("-------------------------------------------------");
        System.out.println("e.g) java CommandLineRunner chap/basic/ 1023");
    }
}