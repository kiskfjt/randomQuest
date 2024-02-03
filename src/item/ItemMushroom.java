package item;

import chr.Chr;
import others.Calc;
import others.IO;

public class ItemMushroom extends Item {

	public ItemMushroom(Chr me) {
		super(me);
		name = "判別不明なキノコ";
	}
	
	/**
	 * 使用対象：味方単体
	 */
	public boolean playerTarget() {
		return IO.selectSingleTarget(me.party.member, me);
	}

	/**
	 * 
	 */
	public void execute() {
		IO.msgln("【%sは%sをつかった！】", me.name, name);
		int num = IO.randomNum(1);
		if (me.targets.get(0).isAlive()) {
			if (num == 0) {
				int mushroomNo = IO.randomNum(2);

				switch (mushroomNo) {
				case 0:// HP小回復
					IO.msgln("なんとそれは普通のきのこだった！");
					rangeMin = 30;
					rangeMax = 50;
					Calc.singleHeal(me, this);
					break;
				case 1:// HP中回復
					IO.msgln("なんとそれはおいしいきのこだった！");
					rangeMin = 80;
					rangeMax = 100;
					Calc.singleHeal(me, this);
					break;
				case 2:// HP全回復
					IO.msgln("なんとそれは栄養満点きのこだった！");
					Calc.maxSingleHeal(me);
					break;
				}
			} else {
				int mushroomNo = IO.randomNum(5);;

				switch (mushroomNo) {
				case 0:
					IO.msgln("なんとそれは毒きのこだった！");
					me.status = me.STATUS_POISONED;
					break;
				case 1:
					IO.msgln("なんとそれは猛毒きのこだった！");
					me.status = me.STATUS_DEADLY_POISONED;
					break;
				case 2:
					IO.msgln("なんとそれはしびれきのこだった！");
					me.status = me.STATUS_PARALYZED;
					break;
				case 3:
					IO.msgln("なんとそれは眠りきのこだった！");
					me.status = me.STATUS_ASLEEP;
					break;
				case 4:
					IO.msgln("なんとそれは混乱きのこだった！");
					me.status = me.STATUS_CONFUSED;
					break;
				case 5:
					IO.msgln("なんとそれは沈黙きのこだった！");
					me.status = me.STATUS_SILENT;
					break;
				}
			}
			IO.removeFromItemList(me, this);
		} else {
			IO.msgln("%sはすでに死んでいる！", me.targets.get(0).name);
		}
		IO.setStatus(me);
	}

}
