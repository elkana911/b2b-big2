package com.big.web.net;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class Sftp {

	private static Sftp sftp = null;
	private String host;
	private String user;
	private String pwd;
	private Integer port;

	private final int timeoutInMillis = 900000;
	Session session = null;
	Channel channel = null;
	ChannelSftp channelSftp = null;

	private Sftp(String host, String user, String pwd, Integer port) {
		this.host = host;
		this.user = user;
		this.pwd = pwd;
		this.port = port;
	}

	public boolean connect() {
		JSch jsch = new JSch();
		try {
			// final byte[] emptyPassPhrase = new byte[0];
			// jsch.addIdentity(username, emptyPassPhrase);
			session = jsch.getSession(user, host, port);
			session.setPassword(pwd);
			session.setTimeout(timeoutInMillis);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			session.sendKeepAliveMsg();
			channel = session.openChannel("sftp");
			channel.connect();
			channelSftp = (ChannelSftp) channel;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public void disconnect() {
		if (session != null) {
			if (session.isConnected()) {
				channel.disconnect();
				session.disconnect();
				session = null;
				channel = null;
			}
		}

	}

	public String ping() {
		JSch jsch = new JSch();
		try {
			// final byte[] emptyPassPhrase = new byte[0];
			// jsch.addIdentity(username, emptyPassPhrase);
			session = jsch.getSession(user, host, port);
			session.setPassword(pwd);
			session.setTimeout(timeoutInMillis);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			session.sendKeepAliveMsg();
			channel = session.openChannel("sftp");
			channel.connect();
			channelSftp = (ChannelSftp) channel;
			return "Success";
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	public void lsDir(String locationFolder) throws SftpException {
		try {
			channelSftp.cd(locationFolder);
		} catch (SftpException e) {
			if (e.id == ChannelSftp.SSH_FX_NO_SUCH_FILE) {
				channelSftp.mkdir(locationFolder);
				channelSftp.cd(locationFolder);
			}
		}
	}

	public List<String> getListFiles(String locationFolder) throws IOException,
			SftpException {
		List<String> fileNameList = new ArrayList<String>();
		channelSftp.cd(channelSftp.getHome());
		locationFolder = locationFolder + "/*.zip";
		Vector<LsEntry> v = (Vector<LsEntry>) channelSftp.ls(locationFolder);
		for (int i = 0; i < v.size(); i++) {
			LsEntry lsEntry = v.get(i);
			if (!lsEntry.getAttrs().isDir()) {
				fileNameList.add(lsEntry.getFilename());
			}
		}
		return fileNameList;
	}

	public void uploadAFile(File file, String locationFolder)
			throws IOException, SftpException {
		channelSftp.cd(channelSftp.getHome());

		if (locationFolder != null)
			lsDir(locationFolder);
		
		FileInputStream fis = new FileInputStream(file);
		channelSftp.put(fis, file.getName());
	}

	/**
	 * Get all files at ftpFolder, then copy it to localFolder. If fileName
	 * exists, it will replaced.
	 * 
	 * @param ftpFolder
	 *            ex: INCOMING
	 * @param localFolder
	 *            ex: c:\\temp
	 * @param deleteSource
	 *            delete after a file downloaded
	 * @return list of downloaded files. ex: list[0] = "c:\\temp\\file1.txt"
	 * @throws SftpException
	 */
	public List<String> downloadAllFiles(String ftpFolder, String localFolder,
			boolean deleteSource) throws SftpException {
		List<String> listFileLocation = new ArrayList<String>();
		try {
			List<String> listFileNames = getListFiles(ftpFolder);
			channelSftp.cd(channelSftp.getHome());

			if (ftpFolder != null)
				channelSftp.cd(ftpFolder);

			FileOutputStream fos = null;
			for (String fileName : listFileNames) {
				String fileLocation = localFolder + "\\" + fileName;

				fos = new FileOutputStream(fileLocation);

				channelSftp.get(fileName, fos);
				listFileLocation.add(fileLocation);

				if (deleteSource)
					channelSftp.rm(fileName);
			}

			if (listFileLocation.size() <= 0)
				return null;
			return listFileLocation;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Get fileName at ftpFolder, then copy it to localFolder. If fileName
	 * exists, it will replaced.
	 * 
	 * @param ftpFolder
	 *            ex: INCOMING
	 * @param localFolder
	 *            ex: c:\\temp
	 * @param fileName
	 *            ex: task.txt
	 * @return
	 * @throws SftpException
	 */
	public String downloadAFile(String ftpFolder, String localFolder,
			String fileName) throws SftpException {
		try {
			channelSftp.cd(channelSftp.getHome());

			if (ftpFolder != null)
				channelSftp.cd(ftpFolder);

			FileOutputStream fos = null;
			String fileLocation = localFolder + "\\" + fileName;
			fos = new FileOutputStream(fileLocation);
			channelSftp.get(fileName, fos);
			String listFileLocation = fileLocation;
			return listFileLocation;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Sftp getInstance(String host, String user, String pwd,
			Integer port) {
		if (sftp == null)
			sftp = new Sftp(host, user, pwd, port);

		return sftp;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public boolean isBusy() {
		return sftp != null;
	}

	@Override
	public String toString() {
		return "MySFTP [host=" + host + ", user=" + user + ", pwd=" + pwd
				+ ", port=" + port + "]";
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String user = "dev1";
		String pwd = "P@ssw0rd";
		Sftp sftp = Sftp.getInstance("127.0.0.1", user, pwd, new Integer(
				7722));
		if (sftp.connect()) {
			System.out.println("connect success");
		} else {
			System.err.println("Unable to connect to " + sftp.toString());
		}
		sftp.disconnect();
	}

}

