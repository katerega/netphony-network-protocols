package es.tid.tests;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import es.tid.bgp.bgp4.open.BGP4CapabilitiesOptionalParameter;
import es.tid.bgp.bgp4.open.BGP4Capability;
import es.tid.bgp.bgp4.open.BGP4OctetsASByteCapabilityAdvertisement;
import es.tid.bgp.bgp4.open.BGP4OptionalParameter;
import es.tid.bgp.bgp4.open.MultiprotocolExtensionCapabilityAdvertisement;
import es.tid.bgp.bgp4.update.fields.LinkNLRI;
import es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs.IGPRouterIDNodeDescriptorSubTLV;
import es.tid.of.DataPathID;
import es.tid.ospf.ospfv2.lsa.LSA;
import es.tid.ospf.ospfv2.lsa.OSPFTEv2LSA;
import es.tid.ospf.ospfv2.lsa.tlv.subtlv.UnreservedBandwidth;
import es.tid.ospf.ospfv2.lsa.tlv.subtlv.complexFields.SwitchingCapabilitySpecificInformationPSC;
import es.tid.pce.pcep.constructs.GeneralizedBandwidthSSON;
import es.tid.pce.pcep.constructs.MetricPCE;
import es.tid.pce.pcep.constructs.NCF;
import es.tid.pce.pcep.constructs.Path;
import es.tid.pce.pcep.constructs.SwitchEncodingType;
import es.tid.pce.pcep.objects.BandwidthRequested;
import es.tid.pce.pcep.objects.BitmapLabelSet;
import es.tid.pce.pcep.objects.EndPointsIPv4;
import es.tid.pce.pcep.objects.Metric;
import es.tid.pce.pcep.objects.ObjectiveFunction;
import es.tid.pce.pcep.objects.PceIdIPv4;
import es.tid.pce.pcep.objects.tlvs.OperatorAssociation;
import es.tid.rsvp.constructs.SenderDescriptor;
import es.tid.rsvp.constructs.gmpls.DWDMWavelengthLabel;
import es.tid.rsvp.objects.IntservSenderTSpec;
import es.tid.rsvp.objects.PolicyData;
import es.tid.rsvp.objects.RSVPHopIPv4;
import es.tid.rsvp.objects.SSONSenderTSpec;
import es.tid.rsvp.objects.SenderTemplateIPv4;
import es.tid.rsvp.objects.SessionIPv4;
import es.tid.rsvp.objects.subobjects.EROSubobject;
import es.tid.rsvp.objects.subobjects.IPv4prefixEROSubobject;

public class TestCommons {
	public static void createAllFields(Object object){
		try {
			//System.out.println("Creating fields of "+object.getClass().getName() );
			List<Field> fieldListNS = new ArrayList<Field>();
			List<Field> fieldList= Arrays.asList(object.getClass().getDeclaredFields());
			//System.out.println("XXXX "+fieldList.size());
			for (Field field : fieldList) {
				
				if (!java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
					fieldListNS.add(field);
					Type ty=field.getGenericType();
					//System.out.println("Type of the field to fill: "+ty);

					if (ty instanceof Class){
						Object o=null;
						Class c =(Class)ty;
						//System.out.println("Class name: "+c.getName()); 
						Method method = object.getClass().getMethod("set"+field.getName().replaceFirst(field.getName().substring(0, 1), field.getName().substring(0, 1).toUpperCase()),field.getType());
						//System.out.println("mi "+method.getName());
						if (c.isPrimitive()){
								fillPrimitive(object,method,ty);
						} else if (c.isArray()){
							
							Class c2=c.getComponentType();
							//System.out.println("c2: "+c2.getName()); 
							o = Array.newInstance(c2, 5);
							
							method.invoke(object, o);
							//System.out.println("FIXME:  ES UN ARRAY OJO ");
						}
						
						else {
						
							//System.out.println("me "+method.getName());
							 if (c.getName().equals("java.lang.String")) {
									o="TEST";
							 } else if  (c.getName().equals("java.net.Inet4Address")) {
									o=Inet4Address.getByName("1.1.1.1");
							 }else if  (c.getName().equals("java.net.Inet6Address")) {
									o=Inet6Address.getByName("1080:0:0:0:8:800:200C:417A");
							 }
							 else if  (c.getName().equals("es.tid.pce.pcep.objects.EndPoints")) {
									o = new EndPointsIPv4();
									createAllFields(o);
							 }else if  (c.getName().equals("es.tid.pce.pcep.objects.Bandwidth")) {
								 o= new BandwidthRequested();
								 createAllFields(o);
								}else if  (c.getName().equals("es.tid.pce.pcep.constructs.GeneralizedBandwidth")) {
									 o= new GeneralizedBandwidthSSON();
									 createAllFields(o);
									}
							 else if  (c.getName().equals("es.tid.pce.pcep.objects.LabelSet")) {
								 o= new BitmapLabelSet();
								 createAllFields(o);
								}
							 else if (c.getName().equals("es.tid.pce.pcep.objects.PceId")){
								 o= new PceIdIPv4();
								 createAllFields(o);
							 }else if (c.getName().equals("es.tid.of.DataPathID")){
								 o= new DataPathID();
								 ((DataPathID)o).setDataPathID("11:22:00:AA:33:BB:11:11");
							 } else if (c.getName().equals("es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs.IGPRouterIDNodeDescriptorSubTLV")){
								 o= new IGPRouterIDNodeDescriptorSubTLV();
								 Inet4Address in=(Inet4Address) Inet4Address.getByName("1.1.1.1");
								 ((IGPRouterIDNodeDescriptorSubTLV)o).setIgp_router_id_type(IGPRouterIDNodeDescriptorSubTLV.IGP_ROUTER_ID_TYPE_OSPF_NON_PSEUDO);
								((IGPRouterIDNodeDescriptorSubTLV)o).setIpv4Address_ospf(in);
							 } else if (c.getName().equals("es.tid.bgp.bgp4.update.fields.NLRI")){
								 o= new LinkNLRI();
								 createAllFields(o);
							 }
							 else if (c.getName().equals("es.tid.ospf.ospfv2.lsa.tlv.subtlv.complexFields.SwitchingCapabilitySpecificInformation")){
								 o= new SwitchingCapabilitySpecificInformationPSC();
								 createAllFields(o);
							 }
							 else if (c.getName().equals("es.tid.ospf.ospfv2.lsa.tlv.subtlv.complexFields.LabelSetField")){
								
								 es.tid.ospf.ospfv2.lsa.tlv.subtlv.complexFields.BitmapLabelSet a = new es.tid.ospf.ospfv2.lsa.tlv.subtlv.complexFields.BitmapLabelSet();
								a.setNumLabels(5);
								 

								 DWDMWavelengthLabel dwdmWavelengthLabel = new DWDMWavelengthLabel();
								 a.setDwdmWavelengthLabel(dwdmWavelengthLabel);
								 a.setBytesBitmap(new byte[5]);
								 o= (Object)a;
								 //createAllFields(o);
							 }
							 else if (c.getName().equals("es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs.UndirectionalDelayVariationDescriptorSubTLV")){
								 System.out.println("FIX UndirectionalDelayVariationDescriptorSubTLV ");
								 o= null;
							 }
							 else if (c.getName().equals("es.tid.ospf.ospfv2.lsa.tlv.subtlv.UnreservedBandwidth")){
								 o= new UnreservedBandwidth();
								 ((UnreservedBandwidth)o).getUnreservedBandwidth()[3]=(float) 4.7;
							 }
							 else if (c.getName().equals("es.tid.rsvp.objects.Session")){
								 o= new SessionIPv4();
								 createAllFields(o);
							 }
							 else if (c.getName().equals("es.tid.rsvp.objects.RSVPHop")){
								 o= new RSVPHopIPv4();
								 createAllFields(o);
							 }
							 else if (c.getName().equals("es.tid.rsvp.objects.SenderTemplate")){
								 o= new SenderTemplateIPv4();
								 createAllFields(o);
							 }
							 else if (c.getName().equals("es.tid.rsvp.objects.SenderTSpec")){
								 o= new SSONSenderTSpec();
								 createAllFields(o);
							 }
							 else if (c.getName().equals("es.tid.rsvp.objects.IntservADSPEC")){
								 o= null;
								 //FIXME: Implement IntservADSPEC
							 }
							 else {
								 //System.out.println("yyyy "+c.getName());
									o = ((Class)ty).newInstance();	
									createAllFields(o);
							 }
							
							method.invoke(object, o);

						}
					}else if (ty instanceof ParameterizedType){
						ParameterizedType pt=(ParameterizedType)ty;
						Type rt=pt.getRawType();
						Type at=pt.getActualTypeArguments()[0];

						if (rt instanceof Class){
							Class ca=(Class)rt;
							
							if (ca.getName().equals("java.util.LinkedList")){
								
								String name="get"+field.getName().replaceFirst(field.getName().substring(0, 1), field.getName().substring(0, 1).toUpperCase());
								String name2="set"+field.getName().replaceFirst(field.getName().substring(0, 1), field.getName().substring(0, 1).toUpperCase());
								
								//System.out.println("name "+name);
								//System.out.println("name2 "+name2);
								//Method method = object.getClass().getMethod("get"+field.getName().replaceFirst(field.getName().substring(0, 1), field.getName().substring(0, 1).toUpperCase()));
								Method method = object.getClass().getMethod(name);
								Method method2 = object.getClass().getMethod(name2,ca);
								
								Object res=method.invoke(object);
								
								Method[] methods =res.getClass().getDeclaredMethods();	
								if  (((Class)at).getName().equals("es.tid.rsvp.objects.subobjects.EROSubobject")) {
									LinkedList<EROSubobject> llero = new LinkedList<EROSubobject>();
									IPv4prefixEROSubobject eroso = new IPv4prefixEROSubobject();
									Inet4Address in=(Inet4Address) Inet4Address.getByName("1.1.1.1");
									eroso.setIpv4address(in);
									eroso.setPrefix(16);
									llero.add(eroso);
									method2.invoke(object, llero);									
								} else if  (((Class)at).getName().equals("es.tid.rsvp.objects.subobjects.RROSubobject")) {
									System.out.println("FIXME: es.tid.rsvp.objects.subobjects.RROSubobject");
								}else if  (((Class)at).getName().equals("es.tid.pce.pcep.objects.subobjects.XROSubobject")) {
									System.out.println("FIXME: es.tid.pce.pcep.objects.subobjects.XROSubobject");
								}else if  (((Class)at).getName().equals("es.tid.pce.pcep.tlvs.PCEPTLV")) {
									System.out.println("FIXME: es.tid.pce.pcep.tlvs.PCEPTLV");
								}
								else if  (((Class)at).getName().equals("es.tid.pce.pcep.objects.Metric")) {
									LinkedList<Metric> ll=new LinkedList<Metric>();
									Object o = ((Class)at).newInstance();
									createAllFields(o);
									ll.add((Metric)o);
									method2.invoke(object,ll);
									
								}
								else if  (((Class)at).getName().equals("es.tid.pce.pcep.objects.ObjectiveFunction")) {
									LinkedList<ObjectiveFunction> ll2=new LinkedList<ObjectiveFunction>();
									Object o = ((Class)at).newInstance();
									createAllFields(o);
									ll2.add((ObjectiveFunction)o);
									method2.invoke(object,ll2);
									
								}
								else if  (((Class)at).getName().equals("es.tid.pce.pcep.constructs.SwitchEncodingType")) {
									LinkedList<SwitchEncodingType> ll=new LinkedList<SwitchEncodingType>();
									Object o = ((Class)at).newInstance();
									createAllFields(o);
									ll.add((SwitchEncodingType)o);
									method2.invoke(object,ll);
									
								}
								else if  (((Class)at).getName().equals("es.tid.pce.pcep.constructs.Path")) {
									LinkedList<Path> ll=new LinkedList<Path>();
									Object o = ((Class)at).newInstance();
									createAllFields(o);
									ll.add((Path)o);
									method2.invoke(object,ll);
									
								}
								else if  (((Class)at).getName().equals("es.tid.pce.pcep.constructs.NCF")) {
									LinkedList<NCF> ll=new LinkedList<NCF>();
									Object o = ((Class)at).newInstance();
									createAllFields(o);
									ll.add((NCF)o);
									method2.invoke(object,ll);
									
								}
								else if  (((Class)at).getName().equals("es.tid.pce.pcep.constructs.MetricPCE")) {
									LinkedList<MetricPCE> ll=new LinkedList<MetricPCE>();
									Object o = ((Class)at).newInstance();
									createAllFields(o);
									ll.add((MetricPCE)o);
									method2.invoke(object,ll);
									
								}
								else if  (((Class)at).getName().equals("es.tid.bgp.bgp4.open.BGP4OptionalParameter")) {
									LinkedList<BGP4OptionalParameter> ll=new LinkedList<BGP4OptionalParameter>();
									Object o = new BGP4CapabilitiesOptionalParameter();
									createAllFields(o);
									ll.add((BGP4OptionalParameter)o);
									method2.invoke(object,ll);
									
								}
								else if  (((Class)at).getName().equals("es.tid.bgp.bgp4.open.BGP4Capability")) {
									LinkedList<BGP4Capability> ll=new LinkedList<BGP4Capability>();
									Object o = new BGP4OctetsASByteCapabilityAdvertisement();
									createAllFields(o);
									ll.add((BGP4Capability)o);
									Object o2 = new MultiprotocolExtensionCapabilityAdvertisement();
									ll.add((BGP4Capability)o2);
									method2.invoke(object,ll);
									
								}
								
								else if (((Class) at).isPrimitive()){
									System.out.println("FIXME: PRIMITIVE "+ ((Class)at).getName());

								}
								else {
									if  (((Class)at).getName().equals("java.lang.Integer")) {
										LinkedList<Integer> ll=new LinkedList<Integer>();
										method2.invoke(object,ll);										
										Integer in=new Integer(3);
										ll.add(in);
									}else if  (((Class)at).getName().equals("java.lang.Long")) {
										Long in=new Long(5);
										methods[0].invoke(res, in);
									}else if  (((Class)at).getName().equals("java.net.Inet4Address")) {
										LinkedList<Inet4Address> ll=new LinkedList<Inet4Address>();
										Inet4Address in=(Inet4Address) Inet4Address.getByName("1.1.1.1");
										ll.add(in);
										method2.invoke(object,ll);
									} else if (((Class)at).getName().equals("es.tid.pce.pcep.objects.tlvs.OperatorAssociation")) {
										LinkedList<OperatorAssociation> operator_associations=new LinkedList<OperatorAssociation>();
										OperatorAssociation oa= new OperatorAssociation();
										oa.setAssocType(0x05);
										oa.setStartAssocID(0x78);
										oa.setRange(0x54);
										operator_associations.add(oa);
										method2.invoke(object, operator_associations);
										
									} else if  (((Class)at).getName().equals("es.tid.ospf.ospfv2.lsa.LSA")) {
										LinkedList<LSA> ll=new LinkedList<LSA>();
										OSPFTEv2LSA os = new OSPFTEv2LSA();
										createAllFields(os);									
										ll.add(os);
										method2.invoke(object,ll);
									}
									else if  (((Class)at).getName().equals("es.tid.rsvp.constructs.SenderDescriptor")) {
										LinkedList<SenderDescriptor> ll=new LinkedList<SenderDescriptor>();
										SenderDescriptor os = new SenderDescriptor();
										createAllFields(os);									
										ll.add(os);
										method2.invoke(object,ll);
									}
									else if  (((Class)at).getName().equals("es.tid.rsvp.objects.PolicyData")) {
										//LinkedList<SenderDescriptor> ll=null;
										//FIXME: PolicyData not implemented
										LinkedList<PolicyData> ll=new LinkedList<PolicyData>();
										PolicyData os = new PolicyData();								
										ll.add(os);
										method2.invoke(object,ll);
									}
									else if  (((Class)at).getName().equals("es.tid.rsvp.objects.IntservSenderTSpec")) {
										LinkedList<IntservSenderTSpec> ll=new LinkedList<IntservSenderTSpec>();
										IntservSenderTSpec os = new IntservSenderTSpec();
										createAllFields(os);									
										ll.add(os);
										method2.invoke(object,ll);
									}
									else {
										
										//Object ll= pt.getRawType(). .newInstance();
										Object ll= ca.newInstance();
										System.out.println("FIXME AND IMPLEMENT IT: "+((Class)at).getName());
										Object o = ((Class)at).newInstance();
										createAllFields(o);
										//Method method3 = ll.getClass().getMethod("add");
										//method3.invoke(ll, o);
										Method[] methodss =ll.getClass().getDeclaredMethods();
										methodss[0].invoke(ll, o);
										method2.invoke(object,ll);	
									}
								}
								
	
							}
			
						}
						

					}

				}		    			    	
			}	
		}
		catch (Exception e) {
			e.printStackTrace();
		} 			
	}

	public static void fillPrimitive(Object object, Method method,Type tyy) {
		try {
			Class ty=(Class)tyy;
			if (ty.getName().equals("int")){

				method.invoke(object, 0);

			}else if (ty.getName().equals("boolean")){

				method.invoke(object,true);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}