package equipment;

import chr.Chr;
import others.IO;

public class EquipmentLidOfPod extends Equipment {

	public EquipmentLidOfPod(Chr me) {
		super(me);
		name = "おなべのふた";
		DEF = 5;
		addStatus();
	}

	@Override
	public boolean playerTarget() {
		return true;
	}

	@Override
	public void execute() {
		IO.msgln("【%sは%sをつかった！】", me.name, name);
		IO.msgln("しかし、なにもおこらなかった！");
	}
}
