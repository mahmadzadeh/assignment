package ea.sample.assignment.domain;

public class Message
{
	private final long id;
	private final String msg;

	public Message( long id, String msg )
	{
		this.id = id;
		this.msg = msg;
	}

	public long getId()
	{
		return id;
	}

	public String getMsg()
	{
		return msg;
	}
}
