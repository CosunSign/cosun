package com.cosun.cosunp.Test;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.DispatchEvents;
import com.jacob.com.STA;
import com.jacob.com.Variant;

/**
 * Created by pc001 on 2016/3/24.
 */
public class ZTest {

	public static void main(String args[]) {

		try {
			ComThread.InitMTA();// ComThread.InitMTA();

			String progId = "zkemkeeper.ZKEM.1";
			Dispatch zkem = new ActiveXComponent(progId);

			SensorEvents events = new SensorEvents();
			new DispatchEvents(zkem, events, progId);

			// connect
			boolean b = Dispatch.call(zkem, "Connect_Net", new Variant("192.168.1.201"), new Variant(4370)).toBoolean();
			System.out.println("链接：" + b);

			Dispatch.call(zkem, "RegEvent", new Variant(1l), new Variant(65535l));

			STA sta = new STA();
			sta.doMessagePump();
			System.in.read();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
