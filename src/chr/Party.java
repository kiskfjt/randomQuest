package chr;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;

import action.ActionMagic;
import action.ActionSkill;
import equipment.Equipment;
import item.Item;
import others.IO;

public class Party {
	public static final int PARTY_KIND_ALLY = 0;
	public static final int PARTY_KIND_ENEMY = 1;
	
	private final int LV_MAX_VALUE = 100;
	private final int MULTI_BY_LV_DEFAULT = 100;
	// 追加スキル数
	private final int NUM_OF_ADDITIONAL_SKILLS = 3;
	// 追加魔法数
	private final int NUM_OF_ADDITIONAL_MAGICS = 3;
	// 初期アイテム数
	private final int NUM_OF_ITEMS = 10;
	
	public ArrayList<Chr> member;
	public Party enemy;
	// パーティー番号
	public int pKind;
	// 属性ポイント初期値
	private final int ELEMENT_POINT_DEFAULT = 0;
	
	public Party(String[] name, int partyKind) {
		member = new ArrayList<>();
		ArrayList<String> chrList = makeChrList();
		ArrayList<String> skillList = makeSkillList();
		ArrayList<String> magicList = makeMagicList();
		ArrayList<String> itemList = makeItemList();
		
		for (int i = 0; i < name.length; i++) {
			Chr chr = null;
			// ジョブをランダム選択する
			chr = (Chr) randomPickup(chrList);
			
			// 名前の設定
			chr.name = name[i];
			
			// レベルをランダム選択する
			chr.Lv = IO.randomNum(LV_MAX_VALUE);
			setStatus(chr);
			
			// 追加スキルをランダム選択する
			selectAdditionalSkills(chr, skillList);
			
			// 追加の魔法をランダムで選択する
			selectAdditionalMagics(chr, magicList);
			
			// そうびをランダム選択する
			selectEquipment(chr);
			
			// そうびからキャラの属性を決定する
			setChrElement(chr);
			
			// どうぐをランダム選択する
			selectItems(chr, itemList);
			
			member.add(chr);
			chr.party = this;
			
			if (partyKind == PARTY_KIND_ALLY) {
				chr.setToPC();
				chr.party.pKind = PARTY_KIND_ALLY;
			} else {
				chr.party.pKind = PARTY_KIND_ENEMY;
			}
			
			//System.out.println(member.get(i).name);
			//System.out.println(battle.party.member.get(i).ATK);
		}
	}
	
	public Party(String[] name, int partyKind, Chr[] enemyChr) {
		member = new ArrayList<>();
		Chr chr = null;
		for (int i = 0; i < name.length; i++) {
			chr = enemyChr[i];
			chr.name = name[i];
			
			member.add(chr);
			chr.party = this;
			
			if (partyKind == PARTY_KIND_ALLY) {
				chr.setToPC();
				chr.party.pKind = PARTY_KIND_ALLY;
			} else {
				chr.party.pKind = PARTY_KIND_ENEMY;
			}
		}
	}
	
	public boolean isZenmetsu() {
		for (Chr c : member) {
			if (c.isAlive()) {
				return false;
			}
		}
		return true;
	}
	
//	private Chr selectJob(String name) {
//		Chr chr = null;
//		int sumOfJobs = 6;
//		int jobNo = IO.randomNum(sumOfJobs - 1);// ジョブ数-1を記入
//		switch (jobNo) {
//		case 0:
//			chr = new BraveChr();
//			break;
//		case 1:
//			chr = new PriestChr();
//			break;
//		case 2:
//			chr = new FighterChr();
//			break;
//		case 3:
//			chr = new WarriorChr();
//			break;
//		case 4:
//			chr = new DancerChr();
//			break;
//		case 5:
//			chr = new LuminaryChr();
//			break;
//		}
//		return chr;
//	}
	
//	private void selectItem(Chr chr) {
//		for (int i = 0; i < MAX_ITEM_NUMBER; i++) {
//			int sumOfItems = 12;
//			int itemNo = IO.randomNum(sumOfItems - 1);
//			
//			switch (itemNo) {
//			case 0:
//				chr.items.add(new ItemMedHerb(chr));
//				break;
//			case 1:
//				chr.items.add(new ItemPhoenixTail(chr));
//				break;
//			case 2:
//				chr.items.add(new ItemStone(chr));
//				break;
//			case 3:
//				chr.items.add(new ItemHealingPotion(chr));
//				break;
//			case 4:
//				chr.items.add(new ItemGreaterHealingPotion(chr));
//				break;
//			case 5:
//				chr.items.add(new ItemMaximTomato(chr));
//				break;
//			case 6:
//				chr.items.add(new ItemMagicPotion(chr));
//				break;
//			case 7:
//				chr.items.add(new ItemGreaterMagicPotion(chr));
//				break;
//			case 8:
//				chr.items.add(new ItemMaximApple(chr));
//				break;
//			case 9:
//				chr.items.add(new ItemElixir(chr));
//				break;
//			case 10:
//				chr.items.add(new ItemPrayerRing(chr));
//				break;
//			case 11:
//				chr.items.add(new ItemMushroom(chr));
//				break;
//			}
//		}
//	}
	
	/**
	 * 追加スキル選択メソッド
	 * @param chr
	 * @param skillList
	 */
	private void selectAdditionalSkills(Chr chr, ArrayList<String> skillList) {
		int count = 0;
		while (count < NUM_OF_ADDITIONAL_SKILLS) {
			ActionSkill skill = (ActionSkill) randomPickup(skillList, chr);
			// キャラが既にそのスキルを持っていれば選択し直す
			if (chr.actions.stream().anyMatch(action -> action.name.equals(skill.name))) {
				continue;
			} else {
				chr.actions.add(skill);
				count++;
			}
		}
	}
	
	/**
	 * 追加魔法選択メソッド
	 * @param chr
	 * @param skillList
	 */
	private void selectAdditionalMagics(Chr chr, ArrayList<String> magicList) {
		int count = 0;
		while (count < NUM_OF_ADDITIONAL_MAGICS) {
			ActionMagic magic = (ActionMagic) randomPickup(magicList, chr);
			// キャラが既にそのスキルを持っていれば選択し直す
			if (chr.actions.stream().anyMatch(action -> action.name.equals(magic.name))) {
				continue;
			} else {
				chr.actions.add(magic);
				count++;
			}
		}
	}
	
	/**
	 * ランダム装備選択メソッド
	 * 武器、鎧、盾、兜、装飾品からランダムに各1種ずつ選ぶ
	 * @param chr
	 */
	private void selectEquipment(Chr chr) {
		chr.equipments.add(selectWeapon(chr));
		chr.equipments.add(selectArmor(chr));
		chr.equipments.add(selectShield(chr));
		chr.equipments.add(selectHelmet(chr));
		chr.equipments.add(selectAccessory(chr));
	}
	
	/**
	 * 武器のランダムな1つのインスタンスを返すメソッド
	 * @param chr
	 * @return
	 */
	private Equipment selectWeapon(Chr chr) {
		return (Equipment) randomPickup(makeWeaponList(), chr);
	}
	
	/**
	 * 鎧のランダムな1つのインスタンスを返すメソッド
	 * @param chr
	 * @return
	 */
	private Equipment selectArmor(Chr chr) {
		return (Equipment) randomPickup(makeArmorList(), chr);
	}
	
	/**
	 * 盾のランダムな1つのインスタンスを返すメソッド
	 * @param chr
	 * @return
	 */
	private Equipment selectShield(Chr chr) {
		return (Equipment) randomPickup(makeShieldList(), chr);
	}
	
	/**
	 * 兜のランダムな1つのインスタンスを返すメソッド
	 * @param chr
	 * @return
	 */
	private Equipment selectHelmet(Chr chr) {
		return (Equipment) randomPickup(makeHelmetList(), chr);
	}
	
	/**
	 * 装飾品のランダムな1つのインスタンスを返すメソッド
	 * @param chr
	 * @return
	 */
	private Equipment selectAccessory(Chr chr) {
		return (Equipment) randomPickup(makeAccessoryList(), chr);
	}
	
	/**
	 * ランダムアイテム選択メソッド
	 * @param chr
	 * @param itemList
	 */
	private void selectItems(Chr chr, ArrayList<String> itemList) {
		for (int i = 0; i < NUM_OF_ITEMS; i++) {
			Item item = (Item) randomPickup(itemList, chr);
			chr.items.add(item);
		}
	}
	
	private void setStatus(Chr chr) {
		chr.HP = chr.maxHP += chr.maxHP * chr.Lv / MULTI_BY_LV_DEFAULT;
		chr.MP = chr.maxMP += chr.maxMP * chr.Lv / MULTI_BY_LV_DEFAULT;
		chr.ATK = chr.baseATK += chr.baseATK * chr.Lv / MULTI_BY_LV_DEFAULT;
		chr.DEF = chr.baseDEF += chr.baseDEF * chr.Lv / MULTI_BY_LV_DEFAULT;
		chr.MAT = chr.baseMAT += chr.baseMAT * chr.Lv / MULTI_BY_LV_DEFAULT;
		chr.MDF = chr.baseMDF += chr.baseMDF * chr.Lv / MULTI_BY_LV_DEFAULT;
		chr.SPD = chr.baseSPD += chr.baseSPD * chr.Lv / MULTI_BY_LV_DEFAULT;
	}
	
	/**
	 * ジョブのクラス名のリスト作成メソッド
	 * @return
	 */
	private ArrayList<String> makeChrList() {
		ArrayList<String> list = new ArrayList<>();
		list.add("chr.jobs.BraveChr");
		list.add("chr.jobs.DancerChr");
		list.add("chr.jobs.FighterChr");
		list.add("chr.jobs.LuminaryChr");
		list.add("chr.jobs.PriestChr");
		list.add("chr.jobs.WarriorChr");
		
		return list;
	}
	
	/**
	 * スキルのクラス名のリスト作成メソッド
	 * @return
	 */
	private ArrayList<String> makeSkillList() {
		ArrayList<String> list = new ArrayList<>();
		
		list.add("action.skills.ActionSkillBakuretsuken");
		list.add("action.skills.ActionSkillDazzleFlash");
		list.add("action.skills.ActionSkillDisruptiveWave");
		list.add("action.skills.ActionSkillFireBreath");
		list.add("action.skills.ActionSkillGigaSlash");
		list.add("action.skills.ActionSkillHardDefense");
		list.add("action.skills.ActionSkillHayabusagiri");
		list.add("action.skills.ActionSkillHustleDance");
		list.add("action.skills.ActionSkillKerplunkDance");
		list.add("action.skills.ActionSkillKiaitame");
		list.add("action.skills.ActionSkillMajingiri");
		list.add("action.skills.ActionSkillMawashigeri");
		list.add("action.skills.ActionSkillMeditation");
		list.add("action.skills.ActionSkillMeteorStrike");
		list.add("action.skills.ActionSkillMinagoroshi");
		list.add("action.skills.ActionSkillMoonSault");
		list.add("action.skills.ActionSkillMorobagiri");
		list.add("action.skills.ActionSkillMysteryWaltz");
		list.add("action.skills.ActionSkillPassionateTango");
		list.add("action.skills.ActionSkillRush");
		list.add("action.skills.ActionSkillSeikenzuki");
		list.add("action.skills.ActionSkillShippuzuki");
		list.add("action.skills.ActionSkillSpookyAura");
		
		return list;
	}
	
	/**
	 * 魔法のクラス名のリスト作成メソッド
	 * @return
	 */
	private ArrayList<String> makeMagicList() {
		ArrayList<String> list = new ArrayList<>();
		
		list.add("action.magics.ActionMagicBreakShield");
		list.add("action.magics.ActionMagicDetoxify");
		list.add("action.magics.ActionMagicGigaLightning");
		list.add("action.magics.ActionMagicGreaterHeal");
		list.add("action.magics.ActionMagicHeal");
		list.add("action.magics.ActionMagicHealingCircle");
		list.add("action.magics.ActionMagicInvincible");
		list.add("action.magics.ActionMagicLightning");
		list.add("action.magics.ActionMagicLightningStorm");
		list.add("action.magics.ActionMagicProtectAll");
		list.add("action.magics.ActionMagicProtection");
		list.add("action.magics.ActionMagicResurrection");
		list.add("action.magics.ActionMagicRevive");
		list.add("action.magics.ActionMagicSongOfNymph");
		
		return list;
	}
	
	/**
	 * アイテムのクラス名のリスト作成メソッド
	 * @return
	 */
	private ArrayList<String> makeItemList() {
		ArrayList<String> list = new ArrayList<>();
		
		list.add("item.ItemElixir");
		list.add("item.ItemGreaterHealingPotion");
		list.add("item.ItemGreaterMagicPotion");
		list.add("item.ItemHealingPotion");
		list.add("item.ItemMagicPotion");
		list.add("item.ItemMaximApple");
		list.add("item.ItemMaximTomato");
		list.add("item.ItemMedHerb");
		list.add("item.ItemMushroom");
		list.add("item.ItemPhoenixTail");
		list.add("item.ItemPrayerRing");
		list.add("item.ItemStone");
		
		return list;
	}
	
	/**
	 * 武器のクラス名のリスト作成メソッド
	 * @return
	 */
	private ArrayList<String> makeWeaponList() {
		ArrayList<String> list = new ArrayList<>();
		
		list.add("equipment.EquipmentFireClaw");
		
		return list;
	}
	
	/**
	 * 鎧のクラス名のリスト作成メソッド
	 * @return
	 */
	private ArrayList<String> makeArmorList() {
		ArrayList<String> list = new ArrayList<>();
		
		list.add("equipment.EquipmentNomalClothes");
		
		return list;
	}
	
	/**
	 * 盾のクラス名のリスト作成メソッド
	 * @return
	 */
	private ArrayList<String> makeShieldList() {
		ArrayList<String> list = new ArrayList<>();
		
		list.add("equipment.EquipmentLidOfPod");
		
		return list;
	}
	
	/**
	 * 兜のクラス名のリスト作成メソッド
	 * @return
	 */
	private ArrayList<String> makeHelmetList() {
		ArrayList<String> list = new ArrayList<>();
		
		list.add("equipment.EquipmentLeatherHat");
		
		return list;
	}
	
	/**
	 * 装飾品のクラス名のリスト作成メソッド
	 * @return
	 */
	private ArrayList<String> makeAccessoryList() {
		ArrayList<String> list = new ArrayList<>();
		
		list.add("equipment.EquipmentAgilityRing");
		list.add("equipment.EquipmentGoldBracelet");
		list.add("equipment.EquipmentMeteoriteBracelet");
		list.add("equipment.EquipmentRubyOfStrength");
		
		return list;
	}
	
	/**
	 * クラス名のリストからランダムでインスタンスを生成するメソッド
	 * 引数がchrのObjectを返す
	 * @param strList
	 * @param chr
	 * @return
	 */
	private Object randomPickup(ArrayList<String> strList, Chr chr) {
		int objectNo = IO.randomNum(strList.size() - 1);
		String str = strList.get(objectNo);
		Object obj = null;
		
		try {
			Class<?> cls = Class.forName(str);
			if (chr != null) {
				obj = cls.getDeclaredConstructor(Chr.class).newInstance(chr);
			} else {
				obj = cls.getDeclaredConstructor().newInstance();
			}
		
		} catch (ClassNotFoundException ex){// Class.forNameで発生する可能性
			ex.printStackTrace();
			throw new RuntimeException();
		} catch (InvocationTargetException ex) {// Class.getDeclaredConstructor()で発生する可能性
			ex.printStackTrace();
			throw new RuntimeException();
		} catch (NoSuchMethodException ex) {// Class.getDeclaredConstructor()で発生する可能性
			ex.printStackTrace();
			throw new RuntimeException();
		} catch (IllegalAccessException ex) {// Class.newInstanceで発生する可能性
			ex.printStackTrace();
			throw new RuntimeException();
		} catch (InstantiationException ex) {// Class.newInstanceで発生する可能性
			ex.printStackTrace();
			throw new RuntimeException();
		}
		return obj;
	}
	
	/**
	 * クラス名のリストからランダムでインスタンスを生成するメソッド
	 * 生成するインスタンスに引数が無いものを返す
	 * @param strList
	 * @return
	 */
	private Object randomPickup(ArrayList<String> strList) {
		return randomPickup(strList, null);
	}
	
	/**
	 * 装備の属性ポイントの合計からキャラの属性を決めるメソッド
	 * 合計点が一番高い属性をキャラの属性とする
	 * ただし合計点が高い属性が2種類の場合は有利属性の方をキャラの属性とする（光と闇は除く）
	 * それ以外の場合はキャラ属性はノーマルとなる。
	 * @param chr
	 */
	private void setChrElement(Chr chr) {
		int sumOfelementPointFire = ELEMENT_POINT_DEFAULT;
		int sumOfelementPointWater = ELEMENT_POINT_DEFAULT;
		int sumOfelementPointThunder = ELEMENT_POINT_DEFAULT;
		int sumOfelementPointEarth = ELEMENT_POINT_DEFAULT;
		int sumOfelementPointAir = ELEMENT_POINT_DEFAULT;
		int sumOfelementPointLight = ELEMENT_POINT_DEFAULT;
		int sumOfelementPointDark = ELEMENT_POINT_DEFAULT;
		
		for (Equipment equipment : chr.equipments) {
			sumOfelementPointFire += equipment.elementPointFire;
			sumOfelementPointWater += equipment.elementPointWater;
			sumOfelementPointThunder += equipment.elementPointThunder;
			sumOfelementPointEarth += equipment.elementPointEarth;
			sumOfelementPointAir += equipment.elementPointAir;
			sumOfelementPointLight += equipment.elementPointLight;
			sumOfelementPointDark += equipment.elementPointDark;
		}
		
		ArrayList<Integer> pointList = new ArrayList<>();
		pointList.add(sumOfelementPointFire);
		pointList.add(sumOfelementPointWater);
		pointList.add(sumOfelementPointThunder);
		pointList.add(sumOfelementPointEarth);
		pointList.add(sumOfelementPointAir);
		pointList.add(sumOfelementPointLight);
		pointList.add(sumOfelementPointDark);
		
		Collections.sort(pointList, Collections.reverseOrder());
		
		ArrayList<Integer> maxPointList = new ArrayList<>();
		
		if (pointList.get(0) == sumOfelementPointFire) {
			maxPointList.add(Chr.CHR_ELEMENT_FIRE);
		} else if (pointList.get(0) == sumOfelementPointWater) {
			maxPointList.add(Chr.CHR_ELEMENT_WATER);
		} else if (pointList.get(0) == sumOfelementPointThunder) {
			maxPointList.add(Chr.CHR_ELEMENT_THUNDER);
		} else if (pointList.get(0) == sumOfelementPointEarth) {
			maxPointList.add(Chr.CHR_ELEMENT_EARTH);
		} else if (pointList.get(0) == sumOfelementPointAir) {
			maxPointList.add(Chr.CHR_ELEMENT_AIR);
		} else if (pointList.get(0) == sumOfelementPointLight) {
			maxPointList.add(Chr.CHR_ELEMENT_LIGHT);
		} else if (pointList.get(0) == sumOfelementPointDark) {
			maxPointList.add(Chr.CHR_ELEMENT_DARK);
		}
		
		
		
		if (maxPointList.size() == 1) {
			chr.element = maxPointList.get(0);
			chr.elementStr = Chr.elementMap.get(chr.element);
		} else if (maxPointList.size() == 2) {
			if (maxPointList.contains(Chr.CHR_ELEMENT_FIRE) && maxPointList.contains(Chr.CHR_ELEMENT_WATER)) {
				chr.element = Chr.CHR_ELEMENT_WATER;
				chr.elementStr = Chr.STR_ELEMENT_WATER;
			} else if (maxPointList.contains(Chr.CHR_ELEMENT_WATER) && maxPointList.contains(Chr.CHR_ELEMENT_THUNDER)) {
				chr.element = Chr.CHR_ELEMENT_THUNDER;
				chr.elementStr = Chr.STR_ELEMENT_THUNDER;
			} else if (maxPointList.contains(Chr.CHR_ELEMENT_THUNDER) && maxPointList.contains(Chr.CHR_ELEMENT_EARTH)) {
				chr.element = Chr.CHR_ELEMENT_EARTH;
				chr.elementStr = Chr.STR_ELEMENT_EARTH;
			} else if (maxPointList.contains(Chr.CHR_ELEMENT_EARTH) && maxPointList.contains(Chr.CHR_ELEMENT_AIR)) {
				chr.element = Chr.CHR_ELEMENT_AIR;
				chr.elementStr = Chr.STR_ELEMENT_AIR;
			} else if (maxPointList.contains(Chr.CHR_ELEMENT_AIR) && maxPointList.contains(Chr.CHR_ELEMENT_FIRE)) {
				chr.element = Chr.CHR_ELEMENT_FIRE;
				chr.elementStr = Chr.STR_ELEMENT_FIRE;
			} else {// 上記以外の場合はリストの0か1の位置の値をランダムで返す
				chr.element = Chr.CHR_ELEMENT_NOMAL;
				chr.elementStr = Chr.STR_ELEMENT_NOMAL;
			}
		} else {
			chr.element = Chr.CHR_ELEMENT_NOMAL;
			chr.elementStr = Chr.STR_ELEMENT_NOMAL;
		}
	}
}
