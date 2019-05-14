package webservice.auxillary.DTO;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "logs")
public class Log extends GenItem implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;

    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "entry_date")
    private Date entry_date;
    
    @Column(name = "logger")
	private String loggerUserName;
	
	@Column(name = "action")
	private LogAction action;
	
	@Column(name = "message")
	private String message;

	public Log() {
    }
    	
	public Log(Date entry_date, String loggerUserName, LogAction action, String message) {
		this.entry_date = entry_date;
		this.loggerUserName = loggerUserName;
		this.action = action;
		this.message = message;
    }
    
    public Date getEntryDate()
	{
		return this.entry_date;
	}
	
	public void setEntryDate(Date entry_date)
	{
		this.entry_date = entry_date;
    }
    
    public String getLoggerUserName()
    {
        return this.loggerUserName;
    }

    public void setLoggerUserName( String loggerUserName )
    {
        this.loggerUserName = loggerUserName;
    }

    public LogAction getLogAction()
    {
        return this.action;
    }

    public void setLogAction( LogAction action )
    {
        this.action = action;
    }

    public String getMessage()
    {
        return this.message;
    }

    public void setMessage( String message )
    {
        this.message = message;
    }
}