package com.javasampleapproach.xmlrestservice.service;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SchedularService {

    @Scheduled(cron="0 0 1 * * *")
    public void scheduleFileTransfer() throws IOException {
          uploadFile();
    }

    private SSHClient setupSshj() throws IOException {
        String remoteHost = "localhost"; // took localhost here just for testing
        String username = "user";
        String password = "password";

        SSHClient client = new SSHClient();
        client.addHostKeyVerifier(new PromiscuousVerifier());
        client.connect(remoteHost);
        client.authPassword(username, password);
        return client;
    }

    private void uploadFile() throws IOException {
        String localFile = "output1.txt";
        String remoteDir = "output/";
        SSHClient sshClient = setupSshj();
        SFTPClient sftpClient = sshClient.newSFTPClient();
        sftpClient.put(localFile, remoteDir + "output.txt");
        sftpClient.close();
        sshClient.disconnect();
    }
}
