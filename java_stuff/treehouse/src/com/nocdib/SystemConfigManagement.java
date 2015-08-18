package com.nocdib;

import java.lang.management.ManagementFactory;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;

 
public class SystemConfigManagement {

 
    public static void main(String[] args) throws MalformedObjectNameException, InstanceNotFoundException, ReflectionException, AttributeNotFoundException, MBeanException {
    	//Get the MBean server
        MBeanServer mbs = 	ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("java.lang:type=ClassLoading");
        //System.out.println(mbs.getDefaultDomain());
        String[] domains = {"LoadedClassCount","ObjectName","TotalLoadedClassCount","UnloadedClassCount","Verbose"};
        AttributeList list = mbs.getAttributes(name, domains);
        for (Attribute a : list.asList())
            //System.out.println(a.getName());
        	System.out.printf("%s -- %s\n", a.getName(), mbs.getAttribute(name, a.getName()));
    }
 
}
