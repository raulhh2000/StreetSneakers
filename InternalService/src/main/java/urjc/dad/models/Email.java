package urjc.dad.models;

public class Email {
	
	private String to;
	private String subject;
	private String content;
	private DataPurchase dataPurchase;
	
	public Email(String to, String subject, String content) {
		super();
		this.to = to;
		this.subject = subject;
		this.content = content;
		this.dataPurchase = null;
	}

	public Email(String to, String subject, String content, DataPurchase dataPurchase) {
		super();
		this.to = to;
		this.subject = subject;
		this.content = content;
		this.dataPurchase = dataPurchase;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public DataPurchase getDataPurchase() {
		return dataPurchase;
	}

	public void setDataPurchase(DataPurchase dataPurchase) {
		this.dataPurchase = dataPurchase;
	}

	@Override
	public String toString() {
		return "Email [to=" + to + ", subject=" + subject + ", content=" + content + ", dataPurchase=" + dataPurchase
				+ "]";
	}

}
