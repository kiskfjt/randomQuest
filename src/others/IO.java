package others;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import action.Action;
import action.ActionBasicAttack;
import action.ActionBasicGuard;
import action.ActionEquipment;
import action.ActionItem;
import action.ActionMagic;
import action.ActionSkill;
import action.skills.ActionSkillCopOut;
import chr.Chr;
import chr.Party;
import equipment.Equipment;
import item.Item;
import item.ItemMushroom;
import item.ItemStone;

public class IO {
	private static Scanner sc = new Scanner(System.in);
	private static final int ATTACK = 0;
	private static final int SKILL = 1;
	private static final int MAGIC = 2;
	private static final int ITEM = 3;
	private static final int GUARD = 4;
	private static final int EQUIP = 5;
	private static final int BACK = 6;

	private static ArrayList<Action> attack;
	private static ArrayList<Action> skill;
	private static ArrayList<Action> magic;
	private static ArrayList<Action> item;
	private static ArrayList<Action> guard;
	private static ArrayList<Action> equip;

	private static int actionNum;
	private static ArrayList<Action> selAction;
	private static String actionName;
	private static int actionSubNum;
	private static final String PROMPT_WAIT = "[ENTER]";
	
	private static final double CHECK_STATUS_UPPER_LIMIT = 0.85;
	private static final double CHECK_STATUS_LOWER_LIMIT = 1.15;
	
	// ステータス異常関連の定数
	private static final int STATUS_TURN_MIN = 3;
	private static final int STATUS_TURN_MAX = 5;
	private static final int ACTION_CANCEL_PROBABILITY = 80;
	private static final double POISONED_DAMAGE_MIN = 0.04;
	private static final double POISONED_DAMAGE_MAX = 0.06;
	private static final double DEADLY_POISONED_DAMAGE_MIN = 0.14;
	private static final double DEADLY_POISONED_DAMAGE_MAX = 0.16;
	
	// 耐性関連の定数
	private static final double MAGIC_RESISTANCE_MIN = 0.5;
	private static final double MAGIC_RESISTANCE_MAX = 1.5;

	public static void printStatus(ArrayList<Chr> member) {
		msgln("*******************************************************");
		msg("%3s", "");
		for (int i = 0; i < member.size(); i++) {
			msg("%3s", "");
			msg("%7s", member.get(i).name);
		}
		ln();
		msg("%3s", "HP");
		for (int i = 0; i < member.size(); i++) {
			msg("%3s", "");
			msg("%6d/%3d", member.get(i).HP, member.get(i).maxHP);
		}
		ln();
		msg("%3s", "MP");
		for (int i = 0; i < member.size(); i++) {
			msg("%3s", "");
			msg("%6d/%3d", member.get(i).MP, member.get(i).maxMP);
		}
		ln();
		msg("%3s", "ATK");
		for (int i = 0; i < member.size(); i++) {
			msg("%3s", "");
			msg("%6d/%3d", member.get(i).ATK, member.get(i).baseATK);
		}
		ln();
		msg("%3s", "DEF");
		for (int i = 0; i < member.size(); i++) {
			msg("%3s", "");
			msg("%6d/%3d", member.get(i).DEF, member.get(i).baseDEF);
		}
		ln();
		msg("%3s", "MAT");
		for (int i = 0; i < member.size(); i++) {
			msg("%3s", "");
			msg("%6d/%3d", member.get(i).MAT, member.get(i).baseMAT);
		}
		ln();
		msg("%3s", "MDF");
		for (int i = 0; i < member.size(); i++) {
			msg("%3s", "");
			msg("%6d/%3d", member.get(i).MDF, member.get(i).baseMDF);
		}
		ln();
		msg("%3s", "SPD");
		for (int i = 0; i < member.size(); i++) {
			msg("%3s", "");
			msg("%6d/%3d", member.get(i).SPD, member.get(i).baseSPD);
		}
		ln();
		msg("%3s", "JOB");
		msg("%3s", "");
		for (int i = 0; i < member.size(); i++) {
			msg("%7s", member.get(i).jobName);
			msg("%1s", "");
		}
		ln();
		msg("%3s", "Lv");
		msg("%5s", "");
		for (int i = 0; i < member.size(); i++) {
			if (member.get(i).status == 0) {
				msg("%6s", member.get(i).Lv);
				msg("%7s", "");
			} else {
				msg("%6s", member.get(i).statusStr);
				msg("%7s", "");
			}
		}

		System.out.println();
		System.out.println("*******************************************************");
	}

	public static boolean selectPCAction(Chr me) {
		sortActions(me);
		int step = 1;
		int maxStep = 3;
		boolean isSetPCAction = false;

		loop: while (step <= maxStep) {
			switch (step) {
			case 0:
				break loop;
			case 1:
				sortActions(me);
				if (selectMainAction(me)) {
					step++;
				} else {
					step--;
				}
				break;
			case 2:
				if (selectSubAction(me)) {
					step++;
				} else {
					step--;
				}
				break;
			case 3:
				if (me.action.target.get()) {
					step++;
					isSetPCAction = true;
				} else {
					if (me.action instanceof ActionBasicAttack) {
						step = 1;// こうげきのターゲット選択時に戻るを選択した場合はメインアクション選択に戻る
					} else {
						step--;// とくぎ、まほう、どうぐ、そうびのターゲット選択時に戻るを選択した場合はサブアクション選択に戻る
					}
				}
				break;
			}
		}
		return isSetPCAction;
	}

	public static void sortActions(Chr me) {
		ArrayList<Action> actions = me.actions;
		Action action = null;

		attack = new ArrayList<>();
		skill = new ArrayList<>();
		magic = new ArrayList<>();
		item = new ArrayList<>();
		guard = new ArrayList<>();
		equip = new ArrayList<>();

		for (int i = 0; i < actions.size(); i++) {
			action = actions.get(i);
			if (action instanceof ActionBasicAttack) {
				attack.add(action);
			} else if (action instanceof ActionSkill) {
				addNomalAndSpecialAction(me, action, skill);
			} else if (action instanceof ActionMagic) {
				addNomalAndSpecialAction(me, action, magic);
			} else if (action instanceof ActionItem) {
				item.add(action);
			} else if (action instanceof ActionBasicGuard) {
				guard.add(action);
			} else if (action instanceof ActionEquipment) {
				equip.add(action);
			}
		}
	}
	
	/**
	 * 特別な特技・魔法は条件を満たした時にのみアクションリストに追加する
	 * @param me
	 * @param action
	 * @param actList
	 */
	public static void addNomalAndSpecialAction(Chr me, Action action, ArrayList<Action> actList) {
		if (action.specialAction == 0) {
			actList.add(action);
		} else if (action.specialAction == 1 && me.HP <= (int) (me.maxHP * action.actionSpecialHPCondition)) {
			actList.add(action);
		}
	}

	public static boolean selectMainAction(Chr me) {
		ArrayList<Item> items = me.items;
		ArrayList<Equipment> equipments = me.equipments;
		boolean isSetMainAction = false;
		while (true) {
			msgln("%sの行動の番号を入力", me.name);
			msg("%d.%s", ATTACK, "こうげき");
			msgln("%2d.%s", SKILL, "とくぎ　");
			msg("%d.%s", MAGIC, "まほう　");
			msgln("%2d.%s", ITEM, "どうぐ　");
			msg("%d.%s", GUARD, "ぼうぎょ");
			msgln("%2d.%s", EQUIP, "そうび　");
			msgln("%d.%s", BACK, "戻る");

			actionNum = inputNumber(BACK);
			if (actionNum == BACK) {
				break;
			}

			if (actionNum == ATTACK) {
				selAction = new ArrayList<>(attack);
				actionName = "こうげき";
			} else if (actionNum == SKILL) {
				selAction = new ArrayList<>(skill);
				actionName = "とくぎ";
			} else if (actionNum == MAGIC) {
				selAction = new ArrayList<>(magic);
				actionName = "まほう";
			} else if (actionNum == ITEM) {
				selAction = new ArrayList<>(item);
				actionName = "どうぐ";
			} else if (actionNum == GUARD) {
				selAction = new ArrayList<>(guard);
				actionName = "ぼうぎょ";
			} else if (actionNum == EQUIP) {
				selAction = new ArrayList<>(equip);
				actionName = "そうび";
			}

			if (selAction.size() == 0) {
				if (actionNum == ATTACK) {
					msgln("こうげきできない！");
					ln();
				} else if (actionNum == SKILL) {
					msgln("とくぎを覚えていない！");
					ln();
				} else if (actionNum == MAGIC) {
					msgln("まほうを覚えていない！");
					ln();
				} else if (actionNum == GUARD) {
					msgln("ぼうぎょできない！");
					ln();
				}
				continue;

			} else if (actionNum == ITEM && items.size() == 0) {
				msgln("どうぐを持っていない！");
				ln();
				continue;
			} else if (actionNum == EQUIP && equipments.size() == 0) {
				msgln("なにもそうびしていない！");
				ln();
				continue;
			} else {
				isSetMainAction = true;
				break;
			}
		}
		return isSetMainAction;
	}

	public static boolean selectSubAction(Chr me) {
		ArrayList<Item> items = me.items;
		ArrayList<Equipment> equipments = me.equipments;

		if (actionNum == ATTACK || actionNum == GUARD) {
			me.action = selAction.get(0);

		} else if (actionNum == ITEM) {
			msgln("%sの使用する%sの番号を入力", me.name, actionName);
			for (int i = 0; i < items.size(); i++) {
				msgln("%d.%s", i, items.get(i).name);
			}
			msgln("%d.%s", items.size(), "戻る");

			// 使うどうぐの決定
			actionSubNum = inputNumber(items.size());

			// 戻るを選択したときはメインアクションの選択に戻る
			if (actionSubNum == items.size()) {
				return false;
			}

			me.action = selAction.get(0);
			me.item = items.get(actionSubNum);
		
		} else if (actionNum == EQUIP) {
			msgln("%sの使用する%sの番号を入力", me.name, actionName);
			for (int i = 0; i < equipments.size(); i++) {
				msgln("%d.%s", i, equipments.get(i).name);
			}
			msgln("%d.%s", equipments.size(), "戻る");

			// 使うそうびの決定
			actionSubNum = inputNumber(equipments.size());

			// 戻るを選択したときはメインアクションの選択に戻る
			if (actionSubNum == equipments.size()) {
				return false;
			}
			
			me.action = selAction.get(0);
			me.equipment = equipments.get(actionSubNum);
		

		} else {
			msgln("%sの使用する%sの番号を入力", me.name, actionName);
			for (int i = 0; i < selAction.size(); i++) {
				msgln("%d.%s", i, selAction.get(i).name);
			}
			msgln("%d.%s", selAction.size(), "戻る");

			// 最終的なアクションの決定
			actionSubNum = inputNumber(selAction.size());

			// 戻るを選択したときはメインアクションの選択に戻る
			if (actionSubNum == selAction.size()) {
				return false;
			}

			me.action = selAction.get(actionSubNum);

			// 呪文決定時点でもMP判定を行う
			if (actionNum == MAGIC && ((ActionMagic) me.action).MPCons > me.MP) {
				msgln("MPがたりない！");
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 攻撃側（me）と防御側（target）の属性の相性に応じて値を返すメソッド
	 * 攻撃側から見て相性がいい場合は1，悪い場合は2，普通の場合は0を返す
	 * @param me
	 * @param target
	 * @return
	 */
	public static int elementCompatibility(Chr me, Chr target) {
		int actElement = me.action.element;
		int tgtElement = target.element;
		int compatibilityNomal = 0;
		
		if (actElement == Action.ACTION_ELEMENT_FIRE) {
			if (tgtElement == Chr.CHR_ELEMENT_AIR) {
				// 属性の相性が良いときは1を返す
				return 1;
			} else if (tgtElement == Chr.CHR_ELEMENT_WATER) {
				// 属性の相性が悪いときは2を返す
				return 2;
			}
		} else if (actElement == Action.ACTION_ELEMENT_WATER) {
			if (tgtElement == Chr.CHR_ELEMENT_FIRE) {
				return 1;
			} else if (tgtElement == Chr.CHR_ELEMENT_THUNDER) {
				return 2;
			}
		} else if (actElement == Action.ACTION_ELEMENT_THUNDER) {
			if (tgtElement == Chr.CHR_ELEMENT_WATER) {
				return 1;
			} else if (tgtElement == Chr.CHR_ELEMENT_EARTH) {
				return 2;
			}
		} else if (actElement == Action.ACTION_ELEMENT_EARTH) {
			if (tgtElement == Chr.CHR_ELEMENT_THUNDER) {
				return 1;
			} else if (tgtElement == Chr.CHR_ELEMENT_AIR) {
				return 2;
			}
		} else if (actElement == Action.ACTION_ELEMENT_AIR) {
			if (tgtElement == Chr.CHR_ELEMENT_THUNDER) {
				return 1;
			} else if (tgtElement == Chr.CHR_ELEMENT_FIRE) {
				return 2;
			}
		} else if (actElement == Action.ACTION_ELEMENT_LIGHT) {
			if (tgtElement == Chr.CHR_ELEMENT_DARK) {
				return 1;
			}
		} else if (actElement == Action.ACTION_ELEMENT_DARK) {
			if (tgtElement == Chr.CHR_ELEMENT_LIGHT) {
				return 1;
			}
		}
		
		// 属性相性が良い、悪い以外の場合は相性ノーマルの0を返す
		return compatibilityNomal;
	}

	/**
	 * 単体生存ターゲット選択メソッド
	 * @param memList
	 * @return
	 */
	public static boolean selectSingleAliveTarget(ArrayList<Chr> memList, Chr me) {
		int targetNum = 0;
		Chr targetChr = null;

		msgln("ターゲットを選択");

		ArrayList<Chr> aliveList = new ArrayList<>();
		for (int i = 0; i < memList.size(); i++) {
			if (memList.get(i).isAlive()) {
				aliveList.add(memList.get(i));
			}
		}

		for (int i = 0; i < aliveList.size(); i++) {
			msgln(i + ".%s", aliveList.get(i).name);
		}
		msgln("%d.%s", aliveList.size(), "戻る");

		targetNum = inputNumber(aliveList.size());

		if (targetNum == aliveList.size()) {// 入力が戻るの場合はfalseを返す
			return false;
		} else {
			targetChr = aliveList.get(targetNum);
			me.targets.add(targetChr);
			return true;
		}
		
//		if (targetChr.isAlive()) {
//			break;
//		} else {
//			msgln("%sはすでにしんでいる！", targetChr.name);
//			continue;
//		}
	}
	
	/**
	 * 単体ターゲット選択メソッド
	 * 対象が死んでいてもターゲットに加える
	 * @param memList
	 * @param me
	 * @return
	 */
	public static boolean selectSingleTarget(ArrayList<Chr> memList, Chr me) {
		int targetNum = 0;
		Chr targetChr = null;

		msgln("ターゲットを選択");

		ArrayList<Chr> targetList = new ArrayList<>();
		for (Chr chr : memList) {
			targetList.add(chr);
		}

		for (int i = 0; i < targetList.size(); i++) {
			msgln(i + ".%s", targetList.get(i).name);
		}
		msgln("%d.%s", targetList.size(), "戻る");

		targetNum = inputNumber(targetList.size());

		if (targetNum == targetList.size()) {// 入力が戻るの場合はfalseを返す
			return false;
		} else {
			targetChr = targetList.get(targetNum);
			me.targets.add(targetChr);
			return true;
		}
	}
	
	/**
	 * ランダム単体選択メソッド
	 * @param targetList
	 * @param me
	 * @return
	 */
	public static boolean selectSingleRandomTarget(ArrayList<Chr> targetList, Chr me) {
		Chr targetChr = null;
		
		while (true) {
			int targetNum = randomNum(targetList.size() - 1);
			targetChr = targetList.get(targetNum);
			if (targetChr.isAlive()) {
				me.targets.add(targetChr);
				break;
			}
		}
		return true;
	}
	
	/**
	 * ランダム単体選択メソッド
	 * ランダムなChrを返す
	 * @param targetList
	 * @param me
	 * @return
	 */
	public static Chr returnSingleRandomTarget(ArrayList<Chr> targetList, Chr me) {
		Chr targetChr = null;
		
		while (true) {
			int targetNum = randomNum(targetList.size() - 1);
			targetChr = targetList.get(targetNum);
			if (targetChr.isAlive()) {
				break;
			}
		}
		return targetChr;
	} 
	
	public static boolean selectMultiRandomTargets(ArrayList<Chr> targetList, Chr me, int numOfTimes) {
		for (int i = 0; i < numOfTimes; i++) {
			selectSingleRandomTarget(targetList, me);
		}
		return true;
	}
	
	public static boolean selectSingleDead(ArrayList<Chr> memList, Chr me) {
		int targetNum = 0;
		Chr targetChr = null;
		
		ArrayList<Chr> deadList = new ArrayList<>();
		for (int i = 0; i < memList.size(); i++) {
			if (memList.get(i).isDead()) {
				deadList.add(memList.get(i));
			}
		}
		
		if (deadList.size() == 0) {
			msgln("誰も死んでいない！");
			ln();
			return false;
		} else {
			msgln("ターゲットを選択");
			
			for (int i = 0; i < deadList.size(); i++) {
				msgln(i + ".%s", deadList.get(i).name);
			}
			msgln("%d.%s", deadList.size(), "戻る");

			targetNum = inputNumber(deadList.size());

			if (targetNum == deadList.size()) {// 入力が戻るの場合はfalseを返す
				return false;
			} else {
				targetChr = deadList.get(targetNum);
				me.targets.add(targetChr);
				return true;
			}
			
		}
	}
	
	/**
	 * 複数ターゲット選択メソッド
	 * 生きているメンバー全員をターゲットリストに加える
	 * @param memList
	 * @param me
	 */
	public static boolean selectMultiAliveTargets(ArrayList<Chr> targetsList, Chr me) {
		for (Chr c : targetsList) {
			if (c.isAlive()) {
				me.targets.add(c);
			}
		}
		return true;
	}
	
	/**
	 * 複数ターゲット選択メソッド
	 * 生死問わず全てのターゲットをリストに加える
	 * @param targetsList
	 * @param me
	 * @return
	 */
	public static boolean selectAllTargets(ArrayList<Chr> targetsList, Chr me) {
		for (Chr c : targetsList) {
			me.targets.add(c);
		}
		return true;
	}

	public static int inputNumber(int max) {
		String str = "";
		int num = 0;
		while (true) {
			try {
				str = sc.nextLine();
				num = Integer.parseInt(str);
				if (num <= max) {
					break;
				} else {
					System.out.println("0から" + max + "までの整数を入力してください");
					continue;
				}
			} catch (NumberFormatException e) {
				System.out.println("0から" + max + "までの整数を入力してください");
				continue;
			}
		}
		return num;
	}
	
	/**
	 * HPが0以下になった時に死亡状態にセットし、倒した時のメッセージを表示する
	 * 第一引数が攻撃側のChr、第二引数が防御側のChrをセットする
	 * @param attacker
	 * @param defender
	 */
	public static void judgeHP(Chr attacker, Chr defender) {
		if (defender.HP <= 0) {
			defender.HP = 0;
			defender.status = defender.STATUS_DEAD;
			defender.statusStr = defender.STATUS_STR_DEAD;
			if (defender.party.pKind == Party.PARTY_KIND_ENEMY) {
				System.out.println(defender.name + "をたおした！");
			} else {
				System.out.println(defender.name + "はしんでしまった！");
			}
		}
	}
	
	public static boolean checkBuff(int statusNo, ArrayList<Chr> targets) {
		for (Chr chr : targets) {
			if (statusNo == Action.STATUS_ATK) {
				if (chr.ATK > chr.baseATK * chr.MAX_ATK_COEF * CHECK_STATUS_UPPER_LIMIT) {
					return true;
				} else if (chr.ATK < chr.baseATK * chr.MIN_ATK_COEF * CHECK_STATUS_LOWER_LIMIT) {
					return true;
				}
			} else if (statusNo == Action.STATUS_DEF) {
				if (chr.DEF > chr.baseDEF * chr.MAX_DEF_COEF * CHECK_STATUS_UPPER_LIMIT) {
					return true;
				} else if (chr.DEF < chr.baseDEF * chr.MIN_DEF_COEF * CHECK_STATUS_LOWER_LIMIT) {
					return true;
				}
			} else if (statusNo == Action.STATUS_MAT) {
				if (chr.MAT > chr.baseMAT * chr.MAX_MAT_COEF * CHECK_STATUS_UPPER_LIMIT) {
					return true;
				} else if (chr.MAT < chr.baseMAT * chr.MIN_MAT_COEF * CHECK_STATUS_LOWER_LIMIT) {
					return true;
				}
			} else if (statusNo == Action.STATUS_MDF) {
				if (chr.MDF > chr.baseMDF * chr.MAX_MDF_COEF * CHECK_STATUS_UPPER_LIMIT) {
					return true;
				} else if (chr.MDF < chr.baseMDF * chr.MIN_MDF_COEF * CHECK_STATUS_LOWER_LIMIT) {
					return true;
				}
			} else if (statusNo == Action.STATUS_SPD) {
				if (chr.SPD > chr.baseSPD * chr.MAX_SPD_COEF * CHECK_STATUS_UPPER_LIMIT) {
					return true;
				} else if (chr.SPD < chr.baseSPD * chr.MIN_SPD_COEF * CHECK_STATUS_LOWER_LIMIT) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 単体状態変更メソッド
	 * 実際に状態変更を行っているのがこれ
	 * 
	 * @param me
	 * @param target
	 * @param statusNo
	 */
	private static void changeSingleStatus(Chr me, Chr target, int statusNo) {
		// 魔法を反射したとき、魔法が成功するかの真偽値
		boolean isSuccess = true;
		
		// 魔法反射判定
		if (me.action instanceof ActionMagic && target.statusMap.containsKey(target.STATUS_MAGIC_BOUNCE)) {
			target = me;
			IO.msgln("光の鏡が呪文を跳ね返した！");
			// 成功率が100以外の時は、成功するかどうか再び判定する
			if (me.action.successRate != Action.SUCCESS_RATE_DEFAULT) {
				if (!IO.probability(me.action.successRate)) {
					isSuccess = false;
					msgln("しかし%sには効かなかった！", target.name);
				}
			}
		}
		
		if (isSuccess) {
			if (statusNo == target.STATUS_POISONED || statusNo == target.STATUS_DEADLY_POISONED) {
				if (target.canBePoisoned) {
					target.status = statusNo;
					setStatus(target);
				}
			} else if (statusNo == target.STATUS_PARALYZED) {
				if (target.canBeParalyzed) {
					target.status = statusNo;
					setStatus(target);
				}
			} else if (statusNo == target.STATUS_ASLEEP) {
				if (target.canBeAsleep) {
					target.status = statusNo;
					setStatus(target);
				}
			} else if (statusNo == target.STATUS_CONFUSED) {
				if (target.canBeConfused) {
					target.status = statusNo;
					setStatus(target);
				}
			} else if (statusNo == target.STATUS_SILENT) {
				if (target.canBeSilent) {
					target.status = statusNo;
					setStatus(target);
				}
			} else {
				int statusTurn = randomNum(me.action.rangeMinInt, me.action.rangeMaxInt);
				target.statusMap.put(statusNo, statusTurn);
			}
		}
	}
	
	/**
	 * 単体状態変更メソッド
	 * このメソッドで処理を受け付ける
	 * @param me
	 * @param statusNo
	 */
	public static void changeSingleStatus(Chr me, int statusNo) {
		Chr target = me.targets.get(0);
		
		if (target.isAlive()) {
			changeSingleStatus(me, target, statusNo);
		} else {
			msgln("%sは死んでいる！", target.name);
		}
	}
	
	/**
	 * 複数ターゲット状態変更メソッド
	 * 単体状態変更メソッドを繰り返して使用している
	 * @param me
	 * @param statusNo
	 */
	public static void changeMultiStatus(Chr me, int statusNo) {
		for (Chr target : me.targets) {
			if (target.isAlive()) {
				if (IO.probability(me.action.successRate)) {
					changeSingleStatus(me, target, statusNo);
				} else {
					IO.msgln("しかし%sには効かなかった！", target.name);
				}
			}
		}
	}
	
	/**
	 * 状態異常になったときにメッセージを表示し、状態異常の継続ターンをセットする
	 * @param me
	 */
	public static void setStatus(Chr me) {
		if (me.status == me.STATUS_POISONED) {
			me.statusStr = me.STATUS_STR_POISONED;
			msgln("%sは毒におかされた！", me.name);
		} else if (me.status == me.STATUS_DEADLY_POISONED) {
			me.statusStr = me.STATUS_STR_DEADLY_POISONED;
			msgln("%sは猛毒におかされた！", me.name);
		} else if (me.status == me.STATUS_PARALYZED) {
			me.statusStr = me.STATUS_STR_PARALYZED;
			msgln("%sは体がまひした！", me.name);
		} else if (me.status == me.STATUS_ASLEEP) {
			me.statusStr = me.STATUS_STR_ASLEEP;
			msgln("%sは眠ってしまった！", me.name);
		} else if (me.status == me.STATUS_CONFUSED) {
			me.statusStr = me.STATUS_STR_CONFUSED;
			msgln("%sは頭が混乱した！", me.name);
		} else if (me.status == me.STATUS_SILENT) {
			me.statusStr = me.STATUS_STR_SILENT;
			msgln("%sは沈黙状態になった！", me.name);
		}
		
		if (me.status != me.STATUS_NOMAL) {
			me.statusTurn = randomNum(STATUS_TURN_MIN, STATUS_TURN_MAX);
		}
	}
	
	/**
	 * ステータス異常回復メソッド
	 * 毒の回復は通常の毒と猛毒両方に有効
	 * @param me
	 * @param clearStatusNo
	 */
	public static void clearStatus(Chr me, int clearStatusNo) {
		if (me.status == clearStatusNo || 
				((me.status == me.STATUS_POISONED || me.status == me.STATUS_DEADLY_POISONED) && 
						(clearStatusNo == me.STATUS_POISONED || clearStatusNo == me.STATUS_DEADLY_POISONED))) {
			me.statusTurn = 0;
			recoverFromAbnormalStatus(me);
		}
	}
	
	/**
	 * 行動する前にステータス異常の影響を実行するメソッド
	 * 行動できない場合はtrueを返す
	 * @param me
	 * @param allList
	 * @return
	 */
	public static boolean doStatusEffectBeforeAction(Chr me, Chr[] allList) {
		boolean cantMove = false;
		ArrayList<Chr> list = new ArrayList<>();
		for (Chr chr : allList) {
			list.add(chr);
		}
		
		if (me.status == me.STATUS_PARALYZED) {
			if (probability(ACTION_CANCEL_PROBABILITY)) {
				cantMove = true;
				msgln("%sは体がまひして動けない！", me.name);
			}
			me.statusTurn--;
		} else if (me.status == me.STATUS_ASLEEP) {
			cantMove = true;
			msgln("%sは眠っている！", me.name);
			me.statusTurn--;
		} else if (me.status == me.STATUS_CONFUSED) {
			if (probability(ACTION_CANCEL_PROBABILITY)) {
				msgln("%sはこんらんしている！", me.name);
				me.targets.clear();
				me.action = null;
				selectSingleRandomTarget(list, me);
				int actionNum = randomNum(4);
				switch (actionNum) {
				case 0:
					me.action = new ActionBasicAttack(me);
					break;
				case 1:
					me.item = new ItemStone(me);
					me.action = new ActionItem(me);
				case 2:
					cantMove = true;
					msgln("%sは逃げ出した！", me.name);
					msgln("しかし、回り込まれてしまった！");
				case 3:
					me.targets.clear();
					me.targets.add(me);
					me.item = new ItemMushroom(me);
					me.action = new ActionItem(me);
					msgln("%sはそこらへんに生えているきのこを食べ始めた！");
				case 4:
					cantMove = true;
					msgln("%sはおびえている！", me.name);
				}
			}
			me.statusTurn--;
		} else if (me.status == me.STATUS_SILENT) {
			if (me.action instanceof ActionMagic) {
				cantMove = true;
				msgln("%sはじゅもんが使えない！", me.name);
			}
			me.statusTurn--;
		}
		return cantMove;
	}
	
	/**
	 * 行動後に発生する状態異常の影響を実行するメソッド
	 * @param me
	 */
	public static void doStatusEffectAfterAction(Chr me) {
		if (me.status == me.STATUS_POISONED) {
			int dmg =  (int) (me.maxHP * IO.randomNum(POISONED_DAMAGE_MIN, POISONED_DAMAGE_MAX));
			me.HP -= dmg;
			msgln("%sは%dの毒ダメージを受けた！", me.name, dmg);
			judgeHP(me.party.enemy.member.get(0), me);
			me.statusTurn--;
		} else if (me.status == me.STATUS_DEADLY_POISONED) {
			int dmg =  (int) (me.maxHP * IO.randomNum(DEADLY_POISONED_DAMAGE_MIN, DEADLY_POISONED_DAMAGE_MAX));
			me.HP -= dmg;
			msgln("%sは%dの猛毒のダメージを受けた！", me.name, dmg);
			judgeHP(me.party.enemy.member.get(0), me);
			me.statusTurn--;
		} else if (me.status == me.STATUS_SING) {
			me.statusTurn--;
		}
	}
	
	/**
	 * 状態異常の状態かつ状態異常のターン数が0の場合、正常な状態に戻す
	 * @param me
	 */
	public static void recoverFromAbnormalStatus(Chr me) {
		if (me.status != 0 && me.statusTurn == 0) {
			if (me.status == me.STATUS_POISONED) {
				msgln("%sの体から毒が消えた！", me.name);
			} else if (me.status == me.STATUS_DEADLY_POISONED) {
				msgln("%sの体から猛毒が消えた！", me.name);
			} else if (me.status == me.STATUS_PARALYZED) {
				msgln("%sの体のしびれがとれた！", me.name);
			} else if (me.status == me.STATUS_ASLEEP) {
				msgln("%sは目を覚ました！", me.name);
			} else if (me.status == me.STATUS_CONFUSED) {
				msgln("%sは正気に戻った！", me.name);
			} else if (me.status == me.STATUS_SILENT) {
				msgln("%sは喋れるようになった！", me.name);
			} else if (me.status == me.STATUS_INVINCIBLE) {
				msgln("%sの鋼鉄化がとけた！", me.name);
			} else if (me.status == me.STATUS_SKIP) {
				me.status = me.STATUS_NOMAL;
				return;
			} else if (me.status == me.STATUS_SING) {
				return;
			}
			me.status = me.STATUS_NOMAL;
			IO.enter();
		}
	}
	
	/**
	 * 眠り状態の時、攻撃を受けたあとに生きていればステータスを正常に戻す
	 * @param me
	 */
	public static void recoverFromAsleep(Chr target) {
		if (target.isAlive() && target.status == target.STATUS_ASLEEP) {
			target.status = target.STATUS_NOMAL;
			msgln("%sは目を覚ました！", target.name);
		}
	}
	
	/**
	 * 状態異常の種類によりターゲット選択とコマンド選択をスキップさせるためのメソッド
	 * @param chr
	 * @return
	 */
	public static boolean checkStatusBeforeCommand(Chr chr) {
		// 歌ってるときはスキップ
		if (chr.status == chr.STATUS_SING) {
			return true;
		// 鋼鉄化のときはスキップ
		} else if (chr.status == chr.STATUS_INVINCIBLE) {
			return true;
		} else if (chr.status == chr.STATUS_SKIP) {
			return true;
		}
		
		return false;
	}
	
	public static void decrementEffectTurns(Chr chr) {
		// 回避率とそのターン数が0より大きいとき
		if (chr.evasionRate > 0 && chr.evasionTurn > 0) {
			chr.evasionTurn--;
			if (chr.evasionTurn == 0) {
				chr.evasionRate = chr.EVASION_RATE_DEFAULT;
			}
		// うけながしがtrueのとき、falseに戻す
		} else if (chr.copOut) {
			chr.copOut = chr.COP_OUT_DEFAULT;
		} else if (chr.statusMap.get(chr.STATUS_MAGIC_BOUNCE) > 0) {
			int value = chr.statusMap.get(chr.STATUS_MAGIC_BOUNCE);
			value--;
			if (value == 0) {
				// マップから削除
				chr.statusMap.remove(chr.STATUS_MAGIC_BOUNCE);
				msgln("%sの光の鏡は消えてしまった！", chr.name);
			} else {
				chr.statusMap.put(chr.STATUS_MAGIC_BOUNCE, value);
			}
		}
	}
	
	/**
	 * 1人のactionをnullにして行動しないようにするメソッド
	 * @param chr
	 */
	public static void clearSingleAction(Chr chr) {
		chr.action = null;
	}
	
	/**
	 * 全員のactionをnullにして行動しないようにするメソッド
	 * @param memList
	 */
	public static void clearAllActions(Chr me) {
		for (Chr chr : me.targets) {
			clearSingleAction(chr);
		}
	}
	
	/**
	 * 自分以外のactionをnullにして行動しないようにするメソッド
	 * @param me
	 */
	public static void clearAllActionsExceptMe(Chr me) {
		for (Chr chr : listExcludeMe(me)) {
			clearSingleAction(chr);
		}
	}
	
	/**
	 * 自分を除いた味方パーティーのリストを返すメソッド
	 * @param me
	 * @return
	 */
	public static ArrayList<Chr> listExcludeMe(Chr me) {
		ArrayList<Chr> listExcludeMe = new ArrayList<>(me.party.member);
		// 味方パーティーリストからmeを削除
		listExcludeMe.remove(listExcludeMe.indexOf(me));
		
		return listExcludeMe;
	}
	
	/**
	 * ターゲットが死んでいるときに再度ランダムにターゲット選択するメソッド
	 * @param targets
	 * @param me
	 */
	public static void changeTargetsRandomlyIfDead(ArrayList<Chr> targets, Chr me) {
		me.targets.stream().filter(target -> target.isDead())
				.forEach(target -> target = returnSingleRandomTarget(targets, me));
	}
	
	/**
	 * 対象単体の魔法効果解除メソッド
	 * @param chr
	 */
	public static void clearSingleMagicEffect(Chr chr) {
		chr.ATK = chr.baseATK;
		chr.DEF = chr.baseDEF;
		chr.MAT = chr.baseMAT;
		chr.MDF = chr.baseMDF;
		chr.SPD = chr.baseSPD;
	}
	
	/**
	 * 対象全体の魔法効果解除メソッド
	 * @param memList
	 */
	public static void clearAllMagicEffects(Chr me) {
		for (Chr chr : me.targets) {
			clearSingleMagicEffect(chr);
		}
		IO.msgln("%sたちにかかっている全ての魔法効果がかき消された！", me.targets.get(0).name);
	}
	
	/**
	 * 魔法耐性変更メソッド
	 * @param me
	 */
	public static void changeMagicResistance(Chr me) {
		for (Chr target : me.targets) {
			if (target.canLowerMagicResistance) {
				if (IO.probability(me.action.successRate)) {
					double before = target.magicResistance;
					double value = 0;
					target.magicResistance -= me.action.effectValue;

					if (target.magicResistance < MAGIC_RESISTANCE_MIN) {
						value = MAGIC_RESISTANCE_MIN - before;
						target.magicResistance = MAGIC_RESISTANCE_MIN;
					} else if (target.magicResistance > MAGIC_RESISTANCE_MAX) {
						value = MAGIC_RESISTANCE_MAX - before;
						target.magicResistance = MAGIC_RESISTANCE_MAX;
					} else {
						value = target.magicResistance - before;
					}

					if (value < 0) {
						IO.msgln("%sの魔法耐性が%d％下がった！", target.name, value * -100);
					} else if (value < 0) {
						IO.msgln("%sの魔法耐性が%d％上がった！", target.name, value * 100);
					} else {
						IO.msgln("%sの魔法耐性はこれ以上変わらない！", target.name);
					}
				} else {
					IO.msgln("%sには効かなかった", target.name);
				}
			} else {
				IO.msgln("%sには効果がないようだ", target.name);
			}
		}
	}
	
	public static Chr changeTarget(Chr me, Chr target) {
		Chr beforeTarget = target;
		// ターゲットが受け流しを使っているときはtrue
		if (target.copOut) {
			ActionSkillCopOut targetAction = (ActionSkillCopOut) target.action;
			int denominator = targetAction.denominator;
			int toEnemy = targetAction.toEnemy;
			int toAlly = targetAction.toAlly;
			
			// 敵に受け流すとき
			if (probability(toEnemy, denominator)) {
				me.targets.clear();
				selectSingleRandomTarget(me.party.enemy.member, me);
				target = me.targets.get(0);
				msgln("%sは%sに受け流した！", beforeTarget.name, target.name);
			// 味方に受け流すとき
			} else if (probability(toAlly, denominator - toEnemy)) {
				me.targets.clear();
				selectSingleRandomTarget(listExcludeMe(me), me);
				target = me.targets.get(0);
				msgln("%sは%sに受け流した！", beforeTarget.name, target.name);
			// 受け流し失敗時
			} else {
				msgln("%sは受け流し損ねた！", target.name);
			}
		}
		return target;
	}
	
	/**
	 * me.targetsから生きているターゲットがいるか判定するメソッド
	 * 1人でも生きていればtrueを返す
	 * @param me
	 * @return
	 */
	public static boolean isTargetAlive(Chr me) {
		boolean isTargetAlive = false;
		for (Chr chr : me.targets) {
			if (chr.isAlive()) {
				isTargetAlive = true;
			}
		}
		return isTargetAlive;
	}
	
	/**
	 * me.targetsから死んでいるターゲットがいるか判定するメソッド
	 * 1人でも死んでいればtrueを返す
	 * @param me
	 * @return
	 */
	public static boolean isTargetDead(Chr me) {
		boolean isTargetDead = false;
		for (Chr chr : me.targets) {
			if (chr.isAlive()) {
				isTargetDead = true;
			}
		}
		return isTargetDead;
	}
	
	/**
	 * ランダム整数生成メソッド
	 * minからmaxまでのランダムな整数を返す
	 * @param min
	 * @param max
	 * @return
	 */
	public static int randomNum(int min, int max) {
		int range = max - min + 1;
		return min + (int) (Math.random() * range);
	}

	/**
	 * ランダム整数生成メソッド
	 * 0からmaxまでのランダムな整数を返す
	 * @param max
	 * @return
	 */
	public static int randomNum(int max) {
		int range = max + 1;
		return (int) (Math.random() * range);
	}
	
//	public static double randomNum(double min, double max) {
//		BigDecimal bdMin = BigDecimal.valueOf(min);
//		BigDecimal bdMax = BigDecimal.valueOf(max);
//		
//		int minDigits = bdMin.precision();
//		int maxDigits = bdMax.precision();
//		int minDecimal = bdMin.scale();
//		int maxDecimal = bdMax.scale();
//		int maxVal = 0;
//		
//		if (minDecimal >= maxDecimal) {
//			maxVal = minDecimal;
//		} else {
//			maxVal = maxDecimal;
//		}
//		
//		int tempMin = (int) (min * Math.pow(10, maxVal));
//		int tempMax = (int) (max * Math.pow(10, maxVal));
//		
//		randomNum(tempMin, tempMax);
//		
//		return min + (int) (Math.random() * range);
//	}
	/**
	 * ランダム小数生成メソッド
	 * minからmaxまでのランダムな小数を返す
	 * @param min
	 * @param max
	 * @return
	 */
	public static double randomNum(double min, double max) {
		double range = max - min;
		Random random = new Random();
		return min + random.nextDouble() * range;
	}
	
	/**
	 * ランダム小数生成メソッド
	 * 0からmaxまでのランダムな小数を返す
	 * @param max
	 * @return
	 */
	public static double randomNum(double max) {
		return randomNum(0, max);
	}
	
	/**
	 * 確率判定メソッド
	 * p%の確率でtrueを返す
	 * @param p
	 * @return
	 */
	public static boolean probability(int p) {
		int random = randomNum(1, 100);
		if (random <= p) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 確率判定メソッド
	 * p/maxの確率でtrueを返す
	 * @param p
	 * @param max
	 * @return
	 */
	public static boolean probability(int p, int max) {
		int random = randomNum(1, max);
		if (random <= p) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * メッセージを出力するメソッド。
	 * 最後に改行を出力する。
	 * @param fmt 出力フォーマット。
	 * @param v 出力する値。
	 * @see msg
	 */
	public static void msgln(String fmt, Object... v) {
		System.out.print(String.format(fmt + "\n", v));
	}

	/**
	 * メッセージを出力するメソッド。
	 * 最後に改行を出力しない。
	 * @param fmt 出力フォーマット。
	 * @param v 出力する値。
	 * @see msgln
	 */
	public static void msg(String fmt, Object... v) {
		System.out.print(String.format(fmt, v));
	}

	/**
	 * 改行のみ行うメソッド
	 */
	public static void ln() {
		System.out.println();
	}

	/**
	 * itemsリストから使用したアイテムのインスタンスを削除するメソッド
	 * @param inputNum
	 */
	public static void removeFromItemList(Chr me, Item item) {
		me.items.remove(item);
	}
	
	/**
	 * エンター入力を待つメソッド
	 */
	public static void enter() {
		msg(PROMPT_WAIT);
		sc.nextLine();
	}
}
