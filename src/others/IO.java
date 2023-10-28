package others;

import java.util.ArrayList;
import java.util.Scanner;

import action.Action;
import action.ActionBasicAttack;
import action.ActionBasicGuard;
import action.ActionEquipment;
import action.ActionItem;
import action.ActionMagic;
import action.ActionSkill;
import chr.Chr;
import chr.Party;
import item.Item;

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

	public static void printStatus(ArrayList<Chr> member) {
		msgln("***********************************");
		msg("%5s", "");
		for (int i = 0; i < member.size(); i++) {
			msg("%5s", member.get(i).name);
		}
		ln();
		msg("%3s", "HP");
		for (int i = 0; i < member.size(); i++) {
			msg("%8d", member.get(i).HP);
		}
		ln();
		msg("%3s", "MP");
		for (int i = 0; i < member.size(); i++) {
			msg("%8d", member.get(i).MP);
		}
		ln();
		msg("%3s", "ATK");
		for (int i = 0; i < member.size(); i++) {
			msg("%8d", member.get(i).ATK);
		}
		ln();
		msg("%3s", "DEF");
		for (int i = 0; i < member.size(); i++) {
			msg("%8d", member.get(i).DEF);
		}
		ln();
		msg("%3s", "DEF");
		for (int i = 0; i < member.size(); i++) {
			msg("%8d", member.get(i).DEF);
		}
		//System.out.println();
		//System.out.printf("%3s", "SPD");
		ln();
		msg("%3s", "job");
		for (int i = 0; i < member.size(); i++) {
			msg("%5s", member.get(i).jobName);
		}

		System.out.println();
		System.out.println("***********************************");
	}

	public static boolean selectPCAction(Chr me) {
		sortActions(me.actions);
		int step = 1;
		int maxStep = 3;
		boolean isSetPCAction = false;

		loop: while (step <= maxStep) {
			switch (step) {
			case 0:
				break loop;
			case 1:
				sortActions(me.actions);
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

	public static void sortActions(ArrayList<Action> actions) {
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
				skill.add(action);
			} else if (action instanceof ActionMagic) {
				magic.add(action);
			} else if (action instanceof ActionItem) {
				item.add(action);
			} else if (action instanceof ActionBasicGuard) {
				guard.add(action);
			} else if (action instanceof ActionEquipment) {
				equip.add(action);
			}
		}
	}

	public static boolean selectMainAction(Chr me) {
		ArrayList<Item> items = me.items;
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
				} else if (actionNum == EQUIP) {
					msgln("なにもそうびしていない！");
					ln();
				}
				continue;

			} else if (actionNum == ITEM && items.size() == 0) {
				msgln("どうぐを持っていない！");
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
	 * 単体ターゲット選択メソッド
	 * @param memList
	 * @return
	 */
	public static boolean selectSingleTarget(ArrayList<Chr> memList, Chr me) {
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
			IO.msgln(i + ".%s", aliveList.get(i).name);
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
	 * 複数ターゲット選択メソッド
	 * 生きているメンバー全員をターゲットリストに加える
	 * @param memList
	 * @param me
	 */
	public static boolean selectMultiTargets(ArrayList<Chr> memList, Chr me) {
		for (Chr c : memList) {
			if (c.HP > 0) {
				me.targets.add(c);
			}
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

	public static void judgeHP(Chr attacker, Chr defender) {
		if (defender.HP <= 0) {
			defender.HP = 0;
			if (attacker.party.pKind == Party.PARTY_KIND_ALLY) {
				System.out.println(defender.name + "をたおした！");
			} else {
				System.out.println(defender.name + "はしんでしまった！");
			}
		}
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

}
