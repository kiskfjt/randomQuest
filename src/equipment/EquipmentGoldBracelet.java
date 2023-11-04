package equipment;

import chr.Chr;
import others.IO;

public class EquipmentGoldBracelet extends Equipment {

	public EquipmentGoldBracelet(Chr me) {
		super(me);
		name = "金のブレスレット";
		HP = 30;
		DEF = 10;
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
