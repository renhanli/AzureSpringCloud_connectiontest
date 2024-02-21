package com.network.connectiontest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.Socket;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.List;

import org.xbill.DNS.ResolverConfig;

@RestController
public class NetworkTestController {
    @GetMapping("/")
	public String index() {
		return "Run DNS resovle and Tcpping tests!";
	}

    @GetMapping("/dns")
	public String dns(@RequestParam String hostname) {
        String result = "Resolve name for "+ hostname + ". </br>";
        try {
            List<InetSocketAddress> dnsServers = ResolverConfig.getCurrentConfig().servers();
            for (InetSocketAddress nameServer : dnsServers)
            {
                result += "Using system selected DNS server:"+ "</br>";
                result += nameServer.getAddress() + "</br>";
            }
            
            InetAddress addr = InetAddress.getByName(hostname);

            result += addr + "</br>";
		} catch (Exception e) {
			result += "DNS resolve Failed! </br>";
            
            StringWriter writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter( writer );
            e.printStackTrace( printWriter );
            printWriter.flush();

            result += writer.toString().replaceAll("(\r\n|\n)", "<br />");
		}

		return result;
	}

    @GetMapping("/tcpping")
	public String tcpping(@RequestParam String target, @RequestParam int port) {
        String result = "Tcpping "+ target + ":"+ port + ".</br>";
        Socket socket = null;
    
        try
        {
            socket = new Socket(target, port);
            socket.setSoTimeout(2*1000);
            result += "Successfully Connected. </br>";

            socket.close();

            result += "Disconnected. </br>";
        }
        catch(Exception e) {
            result += "Connection Failed! </br>";

            StringWriter writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter( writer );
            e.printStackTrace( printWriter );
            printWriter.flush();

            result += writer.toString().replaceAll("(\r\n|\n)", "<br />");
        }
		return result;
	}
}
