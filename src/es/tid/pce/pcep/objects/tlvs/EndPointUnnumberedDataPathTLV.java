package es.tid.pce.pcep.objects.tlvs;

import es.tid.of.DataPathID;
import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.protocol.commons.ByteHandler;

/**
 * UNNUMBERED-Datapath-ENDPOINT TLV.
 * 

   This TLV represent an unnumbered datapath.  This TLV has the same
   semantic as in [RFC3477] The TLV value is encoded as follow (TLV-
   Type=TBA)

      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |              Datapath ID (8 bytes)                            |
     |                                                               |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                       Interface ID (32 bits)                  |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

   This TLV MAY be ignored, in which case a PCRep with NO-PATH should be
   responded, as described in Section 2.5.1.
 * 
 * @author b.jmgj
 *
 */
public class EndPointUnnumberedDataPathTLV extends PCEPTLV{
	public DataPathID switchID;
	public int port;

	public EndPointUnnumberedDataPathTLV()
	{
		this.setTLVType(ObjectParameters.PCEP_TLV_TYPE_UNNUMBERED_ENDPOINT_DATAPATHID);
		this.switchID=new DataPathID();
	}

	public EndPointUnnumberedDataPathTLV(byte[] bytes, int offset) throws MalformedPCEPObjectException
	{
		super(bytes,offset);
		this.switchID=new DataPathID();		
		decode();
	}

	@Override
	public void encode() 
	{
		log.debug("Encoding UnnemberedInterfaceDataPathID EndPoint TLV");
		int length = 8 + 4;

		this.setTLVValueLength(length);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		this.encodeHeader();
		int offset = 4;

		System.arraycopy(ByteHandler.MACFormatStringtoByteArray(switchID.getDataPathID()),0, this.tlv_bytes, offset, 8);

		offset += 8;
		ByteHandler.IntToBuffer(0,offset*8, 32, port,this.tlv_bytes);

		log.debug("switchID after encoding :::: "+switchID.getDataPathID());
		log.info("switchID after encoding :::: "+switchID.getDataPathID());
		log.debug("port after encoding :::: "+port);

	}

	public void decode() throws MalformedPCEPObjectException
	{
		log.debug("Decoding UnnemberedInterfaceDataPathID EndPoint TLV");

		int offset = 4;
		if (this.getTLVValueLength()==0){
			throw new MalformedPCEPObjectException();
		}
		log.debug("TLV Length:" + this.getTLVValueLength());


		byte[] dpid=new byte[8]; 
		System.arraycopy(this.tlv_bytes, offset, dpid, 0, 8);
		switchID.setDataPathID(ByteHandler.ByteMACToString(dpid));
		log.info("jm ver decode switchID: "+switchID.getDataPathID());

		offset += 8;
		port = ByteHandler.easyCopy(0,31,tlv_bytes[offset],tlv_bytes[offset + 1],tlv_bytes[offset + 2],tlv_bytes[offset + 3]);

		log.debug("switchID after decoding :::: "+switchID.getDataPathID());
		log.debug("port after decoding :::: "+port);

	}


	/*
	 * GETTERS AND SETTERS
	 */
	public String getSwitchID() {
		return switchID.getDataPathID();
	}

	public void setSwitchID(String switchID) {
		this.switchID.setDataPathID(switchID);
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}



}
