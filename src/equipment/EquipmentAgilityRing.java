package equipment;

import chr.Chr;
import others.IO;

public class EquipmentAgilityRing extends Equipment {

	public EquipmentAgilityRing(Chr me) {
		super(me);
		name = "はやてのリング";
		SPD = 10;
		elementPointAir = 5;
		addStatus();
	}

	// 特殊効果なし
	public boolean playerTarget() {
		return true;
	}

	// 特殊効果なし
	public void execute() {
		IO.msgln("【%sは%sをつかった！】", me.name, name);
		IO.msgln("しかし、なにもおこらなかった！");
	}

}
