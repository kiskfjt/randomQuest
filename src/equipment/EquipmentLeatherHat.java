package equipment;

import chr.Chr;
import others.IO;

public class EquipmentLeatherHat extends Equipment {

	public EquipmentLeatherHat(Chr me) {
		super(me);
		name = "革の帽子";
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
