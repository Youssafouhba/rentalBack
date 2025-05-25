package ma.zyn.app.zynerator.transverse.data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PushUtil {
    @Value("${remoteRepo.username}")
    private String username;
    @Value("${remoteRepo.email}")
    private String gitHubEmail;
    private String OS = System.getProperty("os.name").toLowerCase();

    public List<String> createExecFile(String dumpFolder, String shFolder, String projectnameInRemoteRepo, String commitMessage, String username, String personalAccessToken) {
        List<String> errors = new ArrayList<>();
        File batchFile = createBatchFile(dumpFolder, shFolder, commitMessage, username, personalAccessToken, projectnameInRemoteRepo, errors);
        String batchFilePath = batchFile.getAbsolutePath();
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();

            if (isLinux()) {
                processBuilder.command("bash", "-c", batchFilePath);
                setExecutePermission(batchFile.toPath());
            } else {
                processBuilder.command("cmd.exe", "/c", batchFilePath);
                processBuilder.command("attrib", "+x", batchFile.getAbsolutePath());
            }
            Process process = processBuilder.start();
            // Read the output and error streams
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();

            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((line = errorReader.readLine()) != null) {
                errors.add(line);
            }
            errorReader.close();
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Batch file executed successfully");
            } else {
                System.out.println("Failed to execute batch file");
            }
        } catch (Exception e) {
            errors.add(e.getMessage());
        }
        return errors;
    }

    private File createBatchFile(String dumpFolder, String shFolder, String commitMessage, String username, String token, String projectNameInRemoteRepo, List<String> errors) {
        String batchFilePath = shFolder + File.separator + "dump";
       if (isLinux()) {
            batchFilePath += ".sh";
        } else {
            batchFilePath += ".bat";
        }
        File batchFile = new File(batchFilePath);
        try {
            if (!batchFile.exists()) {
                batchFile.createNewFile();
            }
        } catch (IOException e) {
            errors.add(e.getMessage());
        }
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(batchFile));
            writer.write("git config --global init.defaultBranch main\n");
            writer.write("git config --global user.name " + username + "\n");
            writer.write("git config --global user.email " + gitHubEmail + "\n");
            //    git config --global user.name "Your Name"
            //    git config --global user.email you@example.com
            if (!(new File(dumpFolder + ".git").exists())) {
                writer.write("git -C " + dumpFolder + " init\n");
            }
            writer.write("git -C " + dumpFolder + " add .\n");
            writer.write("git -C " + dumpFolder + " commit -m \"" + commitMessage + "\"\n");
            String repositoryUrl = "https://" + username + (token == null ? "" : ":" + token) + "@remoteRepo.com/" + username + "/" + projectNameInRemoteRepo;
            writer.write("git -C " + dumpFolder + " push --set-upstream " + repositoryUrl + " main --force\n");
            System.out.println("git cmd file created with success");
        } catch (IOException e) {
            System.out.println("git cmd file created with error");
            errors.add(e.getMessage());
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    errors.add(e.getMessage());
                }
            }
        }
        return batchFile;
    }

    public boolean isLinux() {
        return OS.contains("nix") || OS.contains("nux") || OS.contains("aix");
    }

    private void setExecutePermission(Path filePath) throws IOException {
        // Create a Path object from the file path
        // Set execute permission for owner, group, and others
        Set<PosixFilePermission> permissions = new HashSet<>();
        permissions.add(PosixFilePermission.OWNER_EXECUTE);
        permissions.add(PosixFilePermission.OWNER_READ);
        permissions.add(PosixFilePermission.OWNER_WRITE);
        permissions.add(PosixFilePermission.GROUP_EXECUTE);
        permissions.add(PosixFilePermission.GROUP_READ);
        permissions.add(PosixFilePermission.GROUP_WRITE);
        permissions.add(PosixFilePermission.OTHERS_EXECUTE);
        permissions.add(PosixFilePermission.OTHERS_READ);
        permissions.add(PosixFilePermission.OTHERS_WRITE);
        // Set the permissions on the file
        Files.setPosixFilePermissions(filePath, permissions);
    }
}
