package es.tid.pce.pcep.messages;

/**
 * PCEP message type codes. 
 * <p>Messages from:</p>
 * - @see <a href="https://tools.ietf.org/html/rfc5440">RFC 5440</a>
 * - @see <a href="https://tools.ietf.org/html/rfc5886"> RFC 5886</a>
 * - @see <a href="https://tools.ietf.org/html/rfc8231"> RFC 8231</a>
 * - @see <a href="https://www.iana.org/go/rfc8281">RFC 8281</a>
 * - @see <a href="https://www.iana.org/go/rfc8253">RFC 8253</a>
 * - 
 * @see https://www.iana.org/assignments/pcep/pcep.xhtml#pcep-messages
 * @author Carlos Garcia Argos (cgarcia@novanotio.es) Feb. 11 2010
 * @author Oscar Gonzalez de Dios
 */

public class PCEPMessageTypes {
    
	/*
	 * Open Message
	 * @see <a href="https://tools.ietf.org/html/rfc5440">RFC 5440</a>
	 */
	public static final int MESSAGE_OPEN = 1; // From RFC5440
	
	/*
	 * Keepalive Message
	 * @see <a href="https://tools.ietf.org/html/rfc5440">RFC 5440</a>
	 */
	public static final int MESSAGE_KEEPALIVE = 2; // From RFC5440
	
	/*
	 * Path Computation Request Message
	 * @see <a href="https://tools.ietf.org/html/rfc5440">RFC 5440</a>
	 */
	public static final int MESSAGE_PCREQ = 3; // From RFC5440
	
	/*
	 * Path Computation Reply Message
	 * @see <a href="https://tools.ietf.org/html/rfc5440">RFC 5440</a>
	 */	
	public static final int MESSAGE_PCREP = 4; // From RFC5440
	
	/*
	 * Notification Message
	 * @see <a href="https://tools.ietf.org/html/rfc5440">RFC 5440</a>
	 */	
	public static final int MESSAGE_NOTIFY = 5; // From RFC5440
	
	/*
	 * Error Message
	 * @see <a href="https://tools.ietf.org/html/rfc5440">RFC 5440</a>
	 */	
	public static final int MESSAGE_ERROR = 6; // From RFC5440
	
	/*
	 * Close Message
	 * @see <a href="https://tools.ietf.org/html/rfc5440">RFC 5440</a>
	 */	
	public static final int MESSAGE_CLOSE = 7; // From RFC5440
	
	/*
	 * Path Computation Monitoring Request Message
	 * @see <a href="https://tools.ietf.org/html/rfc5886"> RFC 5886</a>
	 */	
	public static final int MESSAGE_PCMONREQ = 8; 
	
	/*
	 * Path Computation Monitoring Reply Message
	 * @see <a href="https://tools.ietf.org/html/rfc5886"> RFC 5886</a>
	 */	
	public static final int MESSAGE_PCMONREP = 9;
	
	/*
	 * Report Message
	 * @see <a href="https://tools.ietf.org/html/rfc8231"> RFC 8231</a>
	 */	
	public static final int MESSAGE_REPORT = 10;
	
	/*
	 * Update Message
	 * @see <a href="https://tools.ietf.org/html/rfc8231"> RFC 8231</a>
	 */	
	public static final int MESSAGE_UPDATE = 11;
	
	/*
	 * LSP Initiate Request Message
	 * @see <a href="https://www.iana.org/go/rfc8281">RFC 8281</a>
	 */
	public static final int MESSAGE_INITIATE = 12;

	/*
	 * StartTLS Message
	 * @see <a href="https://www.iana.org/go/rfc8253">RFC 8253</a>
	 */
	public static final int MESSAGE_STARTTLS = 13;
	
	//EXPERIMENTAL: TO TALK TO VNTM: 
	public static final int MESSAGE_TE_LINK_SUGGESTION=55;
	public static final int MESSAGE_TE_LINK_SUGGESTION_CONFIRMATION=56;
	public static final int MESSAGE_TE_LINK_TEAR_DOWN_SUGGESTION=57;
	//for testing TM from VNTM
	public static final int MESSAGE_FULL_TOPOLOGY=16;
	
	
}
