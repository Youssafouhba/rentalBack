package ma.zyn.app.zynerator.transverse.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class DumpUtil {

	 private static final String DUMP_DIRECTORY = "/home/app/dump/";
	//private static final String DUMP_DIRECTORY = "C:\\dump\\";
		
    @Autowired
    private FileEncryptionUtil fileEncryptionUtil;
	
    public void execFile() {
        try {
            File destinationDirectory = getDestinationDirectory();
            // Move the dump file to the desired directory structure
            String dumpFileName = "dump_" + new SimpleDateFormat("HH-mm-ss").format(new Date()) + ".sql";
            String dumpFilePath = destinationDirectory.getAbsolutePath() + File.separator + dumpFileName;
            // Execute MySQL dump command
            String[] command = {"bash", "-c", "mysqldump --user=koscy --password=anaKoscy@1987 --databases kosky > " + dumpFilePath};
            Process process = Runtime.getRuntime().exec(command);
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new IOException("Failed to execute mysqldump command, exit code: " + exitCode);
            }
            // Encrypt the dumped file
            fileEncryptionUtil.encryptFile(dumpFilePath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


	public  File getDestinationDirectory() {
		SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
		SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
		SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
		String year = yearFormat.format(new Date());
		String month = monthFormat.format(new Date());
		String day = dayFormat.format(new Date());
		File yearDirectory = new File(DUMP_DIRECTORY + year);
		if (!yearDirectory.exists()) {
			yearDirectory.mkdir();
		}
		File monthDirectory = new File(yearDirectory.getAbsolutePath() + File.separator + month);
		if (!monthDirectory.exists()) {
			monthDirectory.mkdir();
		}
		File dayDirectory = new File(monthDirectory.getAbsolutePath() + File.separator + day);
		if (!dayDirectory.exists()) {
			dayDirectory.mkdir();
		}
		return dayDirectory;
	}

}
