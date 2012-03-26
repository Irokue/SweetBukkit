package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet133Money extends Packet {
	
	public float money;
	public int playerId;

	public Packet133Money(){}
	public Packet133Money(int playerId, float money){
		this.playerId = playerId;
		this.money = money;
	}
	
	@Override
	public void a(DataInputStream datainputstream) throws IOException {
		// TODO Auto-generated method stub
		this.money = datainputstream.readFloat();
		this.playerId = datainputstream.readInt();
	}
	@Override
	public void a(DataOutputStream dataoutputstream) throws IOException {
		// TODO Auto-generated method stub
		dataoutputstream.writeFloat(money);
		dataoutputstream.writeInt(playerId);
	}
	@Override
	public void handle(NetHandler nethandler) {
		// TODO Auto-generated method stub
		nethandler.a(this);
		
	}
	@Override
	public int a() {
		// TODO Auto-generated method stub
		return 12;
	}

}
