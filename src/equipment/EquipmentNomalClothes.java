package equipment;

import chr.Chr;
import others.IO;

public class EquipmentNomalClothes extends Equipment {

	public EquipmentNomalClothes(Chr me) {
		super(me);
		name = "布の服";
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
