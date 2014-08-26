package com.msgme.msgme.data;

import java.util.Hashtable;

import com.msgme.msgme.R;
import com.msgme.msgme.vo.IconData;

public class IconsDataProvider {

	/*
	 * Icons (smiles) definition
	 * 
	 * Icons names can't end with symbol or digit (only Letters!) - for ex. you can't add "like!" or "blink142"
	 * Icon name must be in length of 2 or above
	 * Icon for smile witch is only symbols, like ":)" -  MUST be at length of 2 ( can't be ":-)" )
	 * Icons can't be {[ or {[ or ]} or [}
	 * 
	 */
	public final static Hashtable<String, IconData> htIcons = new Hashtable<String, IconData>() {
		//
		//
		public static final long serialVersionUID = 1L;

	{
    	//   key                           |icon file name             |Name           		|GroupSort      |HiegthDiver |WiegthDiveder
		put ("taxi",		new IconData(R.drawable.icon1, 				"taxi", 				0));
		put ("lol",	    	new IconData(R.drawable.lol_2, 				"lol",   				0,               2f,            2.5f));
		put ("gift",		new IconData(R.drawable.gift, 				"gift", 				0));
		put ("hungry",	   	new IconData(R.drawable.hungry_2, 			"hungry",   			0,               2f,            2f));
		put ("fire",		new IconData(R.drawable.fire, 				"fire", 				0));
		put ("shopping",	new IconData(R.drawable.shopping, 			"shopping", 			0));
		put ("love",		new IconData(R.drawable.love, 				"love", 				0));
		put ("bus",			new IconData(R.drawable.icon2,	 			"bus", 					0));
		put ("hotel",		new IconData(R.drawable.icon3,				"hotel", 				0));
		put ("cruise",		new IconData(R.drawable.icon4,				"cruise", 				0));
		put ("ambulance",	new IconData(R.drawable.icon5,				"ambulance", 			0));
		put ("chopper",		new IconData(R.drawable.icon6,				"chopper", 				0));
		put ("airplane",	new IconData(R.drawable.icon7,				"airplane", 			0));
		put ("passport",	new IconData(R.drawable.icon8,				"passport", 			0));
		put ("trolley",		new IconData(R.drawable.icon9,				"trolley", 				0));
		put ("hamburger",	new IconData(R.drawable.icon10,				"hamburger", 			1));
		put ("hotdog",		new IconData(R.drawable.icon11,				"hotdog", 				1));
		put ("chinesefood",	new IconData(R.drawable.icon12,				"chinesefood", 			1));
		put ("fries",		new IconData(R.drawable.icon13,				"fries", 				1));
		put ("milkshake",	new IconData(R.drawable.icon14,				"milkshake", 			1));
		put ("donuts",		new IconData(R.drawable.icon15,				"donuts", 				1));
		put ("chocolate",	new IconData(R.drawable.icon16,				"chocolate", 			1));
		put ("sauces",		new IconData(R.drawable.icon17,				"sauces", 				1));
		put ("sandwich",	new IconData(R.drawable.icon18,				"sandwich", 			1));
		put ("icecream",	new IconData(R.drawable.icon19,				"icecream", 			1));
		put ("wine",		new IconData(R.drawable.icon20,				"wine", 				2));
		put ("beer",		new IconData(R.drawable.icon21,				"beer", 				2));
		put ("mojito",		new IconData(R.drawable.icon22,				"mojito", 				2));
		put ("glassofwine",	new IconData(R.drawable.icon23,				"glassofwine", 			2));
		put ("whisky",		new IconData(R.drawable.icon24,				"whisky", 				2));
		put ("margarite",	new IconData(R.drawable.icon25,				"margarite", 			2));
		put ("cognac",		new IconData(R.drawable.icon26,				"cognac", 				2));
		put ("inlove",		new IconData(R.drawable.icon27,				"inlove", 				3));
		put ("welldone",	new IconData(R.drawable.icon28,				"welldone", 			3));
		put ("wow",			new IconData(R.drawable.icon29,				"wow", 					3));
		put ("angry",		new IconData(R.drawable.icon30,				"angry", 				3));
		put ("like",		new IconData(R.drawable.icon31,				"like", 				3));
		put ("donttell",	new IconData(R.drawable.icon32,				"donttell", 			3));
		put ("crying",		new IconData(R.drawable.icon33,				"crying", 				3));
		put (":(",			new IconData(R.drawable.icon33,				":(", 					-100));
		put ("):",			new IconData(R.drawable.icon33,				"):",	 				-100));
		put ("asleep",		new IconData(R.drawable.icon34,				"asleep", 				3));
		put (":z",			new IconData(R.drawable.icon34,				":z",					-100));
		put ("z:",			new IconData(R.drawable.icon34,				"z:",					-100));
		put ("sick",		new IconData(R.drawable.icon35,				"sick", 				3));
		put ("nooo",		new IconData(R.drawable.icon36,				"nooo", 				3));
		put ("mmm",			new IconData(R.drawable.icon37,				"mmm", 					3));
		put (":\\",			new IconData(R.drawable.icon37,				":\\", 					-100));
		put ("\\:",			new IconData(R.drawable.icon37,				"\\:", 					-100));
		put ("devil",		new IconData(R.drawable.icon38,				"devil", 				3));
		put ("damm",		new IconData(R.drawable.icon39,				"damm", 				3));
		put (":[",			new IconData(R.drawable.icon39,				":[",					-100));
		put ("]:",			new IconData(R.drawable.icon39,				"]:",					-100));
		put ("(;",			new IconData(R.drawable.icon40,				"(;", 					3));
		put (";)",			new IconData(R.drawable.icon40,				";)", 					-100));		//-100 to not display in icon list to select from
		put ("(:",			new IconData(R.drawable.icon41,				"(:", 					3));
		put (":)",			new IconData(R.drawable.icon41,				":)", 					-100));
		put ("chair",		new IconData(R.drawable.icon42,				"chair", 				4));
		put ("table",		new IconData(R.drawable.icon43,				"table", 				4));
		put ("cabinet",		new IconData(R.drawable.icon44,				"cabinet", 				4));
		put ("sofa",		new IconData(R.drawable.icon45,				"sofa", 				4));
		put ("pillows",		new IconData(R.drawable.icon46,				"pillows", 				4));
		put ("bed",			new IconData(R.drawable.icon47,				"bed", 					4));
		put ("mirror",		new IconData(R.drawable.icon48,				"mirror", 				4));
		put ("clouds",		new IconData(R.drawable.icon49,				"clouds", 				5));
		put ("snow",		new IconData(R.drawable.icon50,				"snow", 				5));
		put ("lightning",	new IconData(R.drawable.icon51,				"glightning", 			5));
		put ("storm",		new IconData(R.drawable.icon52,				"storm", 				5));
		put ("rain",		new IconData(R.drawable.icon53,				"rain", 				5));
		put ("sun",			new IconData(R.drawable.icon54,				"sun", 					5));
		put ("wind",		new IconData(R.drawable.icon55,				"wind", 				5));
		put ("guitar",		new IconData(R.drawable.icon56,				"guitar", 				6));
		put ("headphones",	new IconData(R.drawable.icon57,				"headphones", 			6));
		put ("organ",		new IconData(R.drawable.icon58,				"organ", 				6));
		put ("drums",		new IconData(R.drawable.icon59,				"drums", 				6));
		put ("cd",			new IconData(R.drawable.icon60,				"cd", 					6));
		put ("singer",		new IconData(R.drawable.icon61,				"singer", 				6));
		put ("stereo",		new IconData(R.drawable.icon62,				"stereo", 				6));
		put ("dj",			new IconData(R.drawable.icon63,				"dj", 					6));
		put ("goat",		new IconData(R.drawable.icon64,				"goat", 				7));
		put ("sheep",		new IconData(R.drawable.icon65,				"sheep", 				7));
		put ("roster",		new IconData(R.drawable.icon66,				"roster", 				7));
		put ("cow",			new IconData(R.drawable.icon67,				"cow", 					7));
		put ("pig",			new IconData(R.drawable.icon68,				"pig", 					7));
		put ("dog",			new IconData(R.drawable.icon69,				"dog", 					7));
		put ("cat",			new IconData(R.drawable.icon70,				"cat", 					7));
		put ("seastar",		new IconData(R.drawable.icon71,				"seastar", 				7));
		put ("dolphin",		new IconData(R.drawable.icon72,				"dolphin", 				7));
		put ("octopus",		new IconData(R.drawable.icon73,				"octopus", 				7));
		put ("shell",		new IconData(R.drawable.icon74,				"shell", 				7));
		put ("nemo",		new IconData(R.drawable.icon75,				"nemo", 				7));
		put ("apple",		new IconData(R.drawable.icon76,				"apple", 				8));
		put ("plum",		new IconData(R.drawable.icon77,				"plum", 				8));
		put ("pear",		new IconData(R.drawable.icon78,				"pear", 				8));
		put ("garlic",		new IconData(R.drawable.icon79,				"garlic", 				8));
		put ("onion",		new IconData(R.drawable.icon80,				"onion", 				8));
		put ("carrot",		new IconData(R.drawable.icon81,				"carrot", 				8));
		put ("tomato",		new IconData(R.drawable.icon82,				"tomato", 				8));
		put ("lemon",		new IconData(R.drawable.icon83,				"lemon", 				8));
		put ("gamba",		new IconData(R.drawable.icon84,				"gamba", 				8));
		put ("orange",		new IconData(R.drawable.icon85,				"orange", 				8));
		put ("banana",		new IconData(R.drawable.icon86,				"banana", 				8));
		put ("cherry",		new IconData(R.drawable.icon87,				"cherry", 				8));
		put ("apricot",		new IconData(R.drawable.icon88,				"apricot", 				8));
		put ("watermelon",	new IconData(R.drawable.icon89,				"watermelon", 			8));
		put ("pineapple",	new IconData(R.drawable.icon90,				"pineapple", 			8));
		put ("kiwi",		new IconData(R.drawable.icon91,				"kiwi", 				8));
		put ("avocado",		new IconData(R.drawable.icon92,				"avocado", 				8));
		put ("strawberry",	new IconData(R.drawable.icon93,				"strawberry", 			8));
		put ("raspberries",	new IconData(R.drawable.icon94,				"raspberries", 			8));
		put ("cantaloupe",	new IconData(R.drawable.icon95,				"cantaloupe", 			8));
		put ("olives",		new IconData(R.drawable.icon96,				"olives", 				8));
		put ("rose",		new IconData(R.drawable.icon102,			"rose", 				9));
		put ("rose2",		new IconData(R.drawable.icon103,			"rose2", 				9));
		put ("sunflower",	new IconData(R.drawable.icon104,			"sunflower", 			9));
		put ("home",		new IconData(R.drawable.icon105,			"home", 				10));
		put ("contacts",	new IconData(R.drawable.icon106,			"contacts", 			10));
		put ("printer",		new IconData(R.drawable.icon107,			"printer", 				10));
		put ("plant",		new IconData(R.drawable.icon108,			"plant", 				10));
		put ("phone",		new IconData(R.drawable.icon109,			"phone", 				10));
		put ("diploma",		new IconData(R.drawable.icon110,			"diploma", 				10));
		put ("soccer",		new IconData(R.drawable.icon111,			"soccer", 				11));
		put ("basketball",	new IconData(R.drawable.icon112,			"basketball", 			11));
		put ("golf",		new IconData(R.drawable.icon113,			"golf", 				11));
		put ("football",	new IconData(R.drawable.icon114,			"football", 			11));
		put ("bowling",		new IconData(R.drawable.icon115,			"bowling", 				11));
		put ("snooker",		new IconData(R.drawable.icon116,			"snooker", 				11));
		put ("tennis",		new IconData(R.drawable.icon117,			"tennis", 				11));
		put ("3d",			new IconData(R.drawable.icon119,			"3d", 					12));
		put ("film",		new IconData(R.drawable.icon120,			"film", 				12));
		put ("action",		new IconData(R.drawable.icon121,			"action", 				12));
		put ("director",	new IconData(R.drawable.icon122,			"director", 			12));
		put ("tv",			new IconData(R.drawable.icon123,			"tv", 					12));
		put ("hat",			new IconData(R.drawable.icon124,			"hat", 					13));
		put ("shorts",		new IconData(R.drawable.icon125,			"shorts", 				13));
		put ("gloves",		new IconData(R.drawable.icon126,			"gloves", 				13));
		put ("scarf",		new IconData(R.drawable.icon127,			"scarf", 				13));
		put ("dress",		new IconData(R.drawable.icon128,			"dress", 				13));
		put ("bracelet",	new IconData(R.drawable.icon129,			"bracelet", 			13));
		put ("jacket",		new IconData(R.drawable.icon130,			"jacket", 				13));
		put ("cap",			new IconData(R.drawable.icon131,			"cap", 					13));
		put ("socks",		new IconData(R.drawable.icon132,			"socks", 				13));
		put ("tie",			new IconData(R.drawable.icon133,			"tie", 					13));
		put ("bowtie",		new IconData(R.drawable.icon134,			"bowtie", 				13));
		put ("hotchoco",	new IconData(R.drawable.icon135,			"hotchoco", 			14));
		put ("espresso",	new IconData(R.drawable.icon136,			"espresso", 			14));
		put ("coffee2go",	new IconData(R.drawable.icon137,			"coffee2go", 			14));
		put ("tea",			new IconData(R.drawable.icon138,			"tea", 					14));
		put ("coffee",		new IconData(R.drawable.icon139,			"coffee", 				14));
		put ("cookies",		new IconData(R.drawable.icon140,			"cookies", 				14));
		put ("cappuchino",	new IconData(R.drawable.icon141,			"cappuchino", 			14));
		put ("calculator",	new IconData(R.drawable.icon142,			"calculator", 			15));
		put ("dollar",		new IconData(R.drawable.icon143,			"dollar", 				15));
		put ("cheque",		new IconData(R.drawable.icon144,			"cheque", 				15));
		put ("safebox",		new IconData(R.drawable.icon145,			"safebox", 				15));
		put ("wallet",		new IconData(R.drawable.icon146,			"wallet", 				15));
		put ("credit",		new IconData(R.drawable.icon147,			"credit", 				15));
		put ("chess",		new IconData(R.drawable.icon148,			"chess", 				16));
		put ("draughts",	new IconData(R.drawable.icon149,			"draughts", 			16));
		put ("dice",		new IconData(R.drawable.icon150,			"dice", 				16));
		put ("pingpong",	new IconData(R.drawable.icon151,			"pingpong", 			16));
		put ("domino",		new IconData(R.drawable.icon152,			"domino", 				16));
		put ("goggles",		new IconData(R.drawable.icon153,			"goggles", 				17));
		put ("sandcastle",	new IconData(R.drawable.icon154,			"sandcastle", 			17));
		put ("coconut",		new IconData(R.drawable.icon155,			"coconut", 				17));
		put ("sailboat",	new IconData(R.drawable.icon156,			"sailboat", 			17));
		put ("bikini",		new IconData(R.drawable.icon157,			"bikini", 				17));
		put ("lotion",		new IconData(R.drawable.icon158,			"lotion", 				17));
		put ("gym",			new IconData(R.drawable.gym_72,				"gym", 				0));
		put ("hello",		new IconData(R.drawable.hello_72_2,			"hello",			0));
		put ("hi",			new IconData(R.drawable.hello_72_2,			"hi",				-100));
		put ("hey",			new IconData(R.drawable.hello_72_2,			"hey",				-100));
		put ("hospital",	new IconData(R.drawable.hospital_72_2,		"hospital",			0));
		put ("milk",		new IconData(R.drawable.milk_72,			"milk",				0));
		put ("sneakers",	new IconData(R.drawable.sneakers_72,		"sneakers",			0));
		put ("timer",		new IconData(R.drawable.time_72,			"timer",			0));
		put ("tired",		new IconData(R.drawable.tired_72,			"tired",			0));
		put ("you",			new IconData(R.drawable.you_72,				"you",				0));
		put ("finger",		new IconData(R.drawable.finger,				"finger",			0));
		put ("volleyball",	new IconData(R.drawable.volleyball,			"volleyball",		0));
		put ("pistol",		new IconData(R.drawable.pistol,				"pistol",			0));
		put ("shot",		new IconData(R.drawable.chot,				"shot",				0));
		put ("zombie",		new IconData(R.drawable.zombie,				"zombie",			0));
		put ("star",		new IconData(R.drawable.star,				"star",				0));
		put ("stars",		new IconData(R.drawable.star,				"stars",			-100));
		put ("baseball",	new IconData(R.drawable.baseball,			"baseball",			0));
		put ("money",		new IconData(R.drawable.money,				"money",			0));
		
		put ("peace",		new IconData(R.drawable.peace,				"peace",			0));
		put ("synagogue",	new IconData(R.drawable.synagogue,			"synagogue",		0));
		put ("pills",		new IconData(R.drawable.pills,				"pills",			0));
		put ("mosque",		new IconData(R.drawable.mosque,				"mosque",			0));
		put ("monkey",		new IconData(R.drawable.monkey,				"monkey",			0));
		put ("elephant",	new IconData(R.drawable.elephant,			"elephant",			0));
		put ("condom",		new IconData(R.drawable.condom,				"condom",			0));
		put ("church",		new IconData(R.drawable.church,				"church",			0));
		put ("butterfly",	new IconData(R.drawable.butterfly,			"butterlfy",		0));
		put ("baby",		new IconData(R.drawable.baby,				"baby",				0));
		
		put ("argentina",		new IconData(R.drawable.argentina,			"argentina",		-100));
		put ("australia",		new IconData(R.drawable.australia,			"australia",		-100));
		put ("belgium",			new IconData(R.drawable.belgium,			"belgium",			-100));
		put ("brazil",			new IconData(R.drawable.brazil,				"brazil",			-100));
		put ("bulgaria",		new IconData(R.drawable.bulgaria,			"bulgaria",			-100));
		put ("cameron",			new IconData(R.drawable.cameron,			"cameron",			-100));
		put ("canada",			new IconData(R.drawable.canada,				"canada",			-100));
		put ("china",			new IconData(R.drawable.china,				"china",			-100));
		put ("croatia",			new IconData(R.drawable.croatia,			"croatia",			-100));
		put ("cyrup",			new IconData(R.drawable.cyrup,				"cyrup",			-100));
		put ("czech",			new IconData(R.drawable.czech,				"czech",			-100));
		put ("denemark",		new IconData(R.drawable.denemark,			"denemark",			-100));
		put ("england",			new IconData(R.drawable.england,			"england",			-100));
		put ("estonia",			new IconData(R.drawable.estonia,			"estonia",			-100));
		put ("finland",			new IconData(R.drawable.finland,			"finland",			-100));
		put ("france",			new IconData(R.drawable.france,				"france",			-100));
		put ("germany",			new IconData(R.drawable.germany,			"germany",			-100));
		put ("greece",			new IconData(R.drawable.greece,				"greece",			-100));
		put ("india",			new IconData(R.drawable.india,				"india",			-100));
		put ("ireland",			new IconData(R.drawable.ireland,			"ireland",			-100));
		put ("israel",			new IconData(R.drawable.israel,				"israel",			-100));
		put ("italy",			new IconData(R.drawable.italy,				"italy",			-100));
		put ("japan",			new IconData(R.drawable.japan,				"japan",			-100));
		put ("mexico",			new IconData(R.drawable.mexico,				"mexico",			-100));
		put ("netherlands",		new IconData(R.drawable.netherlands,		"netherlands",		-100));
		put ("norway",			new IconData(R.drawable.norway,				"norway",			-100));
		put ("poland",			new IconData(R.drawable.poland,				"poland",			-100));
		put ("portugal",		new IconData(R.drawable.portugal,			"portugal",			-100));
		put ("romania",			new IconData(R.drawable.romania,			"romania",			-100));
		put ("russia",			new IconData(R.drawable.russia,				"russia",			-100));
		put ("saudi_arabia",	new IconData(R.drawable.saudi_arabia,		"saudi_arabia",		-100));
		put ("scotland",		new IconData(R.drawable.scotland,			"scotland",			-100));
		put ("slovania",		new IconData(R.drawable.slovania,			"slovania",			-100));
		put ("south_korea",		new IconData(R.drawable.south_korea,		"south_korea",		-100));
		put ("spain",			new IconData(R.drawable.spain,				"spain",			-100));
		put ("sweden",			new IconData(R.drawable.sweden,				"sweden",			-100));
		put ("turkey",			new IconData(R.drawable.turkey,				"turkey",			-100));
		put ("uk",				new IconData(R.drawable.uk,					"uk",				-100));
		put ("usa",				new IconData(R.drawable.usa,				"usa",				-100));
		put ("wales",			new IconData(R.drawable.wales,				"wales",			-100));
		
		put ("angel",			new IconData(R.drawable.angel,			"angel",		0));
		put ("brokenheart",     new IconData(R.drawable.brokenheart,    "brokenheart",  0));
		put ("cigarette",       new IconData(R.drawable.cigarette,	    "cigarette",    0));
		put ("donkey",          new IconData(R.drawable.donkey,		    "donkey",		0));
		put ("firefighter",     new IconData(R.drawable.firefighter,	"firefighter",	0));
		put ("horse",           new IconData(R.drawable.horse,		    "horse",		0));
		put ("joystick",        new IconData(R.drawable.joystick,	    "joystick",	    0));
		put ("light",           new IconData(R.drawable.light,		    "light",		0));
		put ("ok",              new IconData(R.drawable.ok,			    "ok",			0));
		put ("piano",           new IconData(R.drawable.piano,		    "piano",		0));
		put ("popcorn",         new IconData(R.drawable.popcorn,	    "popcorn",		0));
		put ("radio",           new IconData(R.drawable.radio,		    "radio",		0));
		put ("soldier",         new IconData(R.drawable.soldier,		"soldier",		0));
		put ("swan",            new IconData(R.drawable.swan,		    "swan",		    0));
		put ("toilet",          new IconData(R.drawable.toilet,		    "toilet",		0));
	}};

	public final static Hashtable<String, IconData> htIconsHeb = new Hashtable<String, IconData>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	{
		put ("����",	    	new IconData(R.drawable.gift, 				"����", 		    		0));
		put ("���",	    	new IconData(R.drawable.hungry_2, 			"���",   				0,             2f,          2f));
		put ("��",		    new IconData(R.drawable.fire, 				"��", 		    		0));
		put ("�����",	        new IconData(R.drawable.shopping, 			"�����", 		        	0));
		put ("����",	    	new IconData(R.drawable.love, 				"����", 		    		-100));
		put ("�����",		new IconData(R.drawable.love, 				"�����", 				0));
		put ("�����",			new IconData(R.drawable.icon1, 				"�����",					0));
		put ("�������",		new IconData(R.drawable.icon2,	 			"�������",				0));
		put ("����",			new IconData(R.drawable.icon3,				"����",					0));
		put ("����",			new IconData(R.drawable.icon4,				"����",					0));
		put ("�������",		new IconData(R.drawable.icon5,				"�������",				0));
		put ("����",			new IconData(R.drawable.icon6,				"����",					0));
		put ("����",			new IconData(R.drawable.icon7,				"����",					0));
		put ("�����",			new IconData(R.drawable.icon8,				"�����",					0));
		put ("�����",			new IconData(R.drawable.icon9,				"�����",					0));
		put ("�������",		new IconData(R.drawable.icon10,				"�������",				1));
		put ("������",		new IconData(R.drawable.icon11,				"������",					1));
		put ("��������",		new IconData(R.drawable.icon12,				"��������",				1));
		put ("�'���",		new IconData(R.drawable.icon13,				"�'���",					1));
		put ("��������",		new IconData(R.drawable.icon14,				"��������",				1));
		put ("�����",			new IconData(R.drawable.icon15,				"�����",					1));
		put ("������",		new IconData(R.drawable.icon16,				"������",					1));
		put ("�����",			new IconData(R.drawable.icon17,				"�����",					1));
		put ("�������",		new IconData(R.drawable.icon18,				"�������",					1));
		put ("�����",			new IconData(R.drawable.icon19,				"�����",					1));
		put ("���",			new IconData(R.drawable.icon20,				"���",					2));
		put ("����",			new IconData(R.drawable.icon21,				"����",					2));
		put ("������",			new IconData(R.drawable.icon22,				"������",					2));
		put ("������",			new IconData(R.drawable.icon23,				"������",					2));
		put ("������",			new IconData(R.drawable.icon24,				"������",					2));
		put ("�������",		new IconData(R.drawable.icon25,				"�������",				2));
		put ("������",		new IconData(R.drawable.icon26,				"������",					2));
		put ("�����",		new IconData(R.drawable.icon27,				"�����",					3));
		put ("������",		new IconData(R.drawable.icon27,				"������",				3));
		put ("�������",		new IconData(R.drawable.icon28,				"�������",				3));
		put ("����",			new IconData(R.drawable.icon29,				"����",					3));
		put ("����",			new IconData(R.drawable.icon30,				"����",					3));
		put ("�����",		new IconData(R.drawable.icon30,				"�����",					3));
		put ("����",			new IconData(R.drawable.icon31,				"����",					3));
		put ("������",		new IconData(R.drawable.icon32,				"������",				3));
		put ("�������",		new IconData(R.drawable.icon32,				"�������",				3));
		put ("����",			new IconData(R.drawable.icon33,				"����",					3));
		put (":(",			new IconData(R.drawable.icon33,				":(",					-100));
		put ("):",			new IconData(R.drawable.icon33,				"):",					-100));
		put ("����",			new IconData(R.drawable.icon34,				"����",					3));
		put ("�����",		new IconData(R.drawable.icon34,				"�����",					3));
		put (":�",			new IconData(R.drawable.icon34,				":�",					-100));
		put ("�:",			new IconData(R.drawable.icon34,				"�:",					-100));
		put ("����",			new IconData(R.drawable.icon35,				"����",					3));
		put ("���",			new IconData(R.drawable.icon36,				"���",					3));
		put ("���",			new IconData(R.drawable.icon37,				"���",					3));
		put (":\\",			new IconData(R.drawable.icon37,				":\\",					-100));
		put ("\\:",			new IconData(R.drawable.icon37,				"\\:",					-100));
		put ("���",			new IconData(R.drawable.icon38,				"���",					3));
		put ("���",			new IconData(R.drawable.icon39,				"���",					3));
		put (":[",			new IconData(R.drawable.icon39,				":[",					-100));
		put ("]:",			new IconData(R.drawable.icon39,				"]:",					-100));
		put ("(;",			new IconData(R.drawable.icon40,				"(;",					3));
		put (";)",			new IconData(R.drawable.icon40,				";)", 					-100));		//-100 to not display in icon list to select from
		put ("(:",			new IconData(R.drawable.icon41,				"(:",					3));
		put (":)",			new IconData(R.drawable.icon41,				":)", 					-100));
		put ("���",			new IconData(R.drawable.icon42,				"���",					4));
		put ("�����",			new IconData(R.drawable.icon43,				"�����",					4));
		put ("������",		new IconData(R.drawable.icon44,				"������",					4));
		put ("���",			new IconData(R.drawable.icon45,				"���",					4));
		put ("����",			new IconData(R.drawable.icon46,				"����",					4));
		put ("�����",			new IconData(R.drawable.icon46,				"�����",					4));
		put ("����",			new IconData(R.drawable.icon47,				"����",					4));
		//put ("����",			new IconData(R.drawable.icon48,				"����",					4));
		put ("�����",			new IconData(R.drawable.icon49,				"�����",					5));
		put ("�����",			new IconData(R.drawable.icon49,				"�����",					5));
		put ("���",			new IconData(R.drawable.icon50,				"���",					5));
		put ("�����",			new IconData(R.drawable.icon51,				"�����",					5));
		put ("����",			new IconData(R.drawable.icon52,				"����",					5));
		put ("���",			new IconData(R.drawable.icon53,				"���",					5));
		put ("����",			new IconData(R.drawable.icon53,				"����",					5));
		put ("���",			new IconData(R.drawable.icon54,				"���",					5));
		put ("���",			new IconData(R.drawable.icon55,				"���",					5));
		put ("�����",			new IconData(R.drawable.icon56,				"�����",					6));
		put ("�������",		new IconData(R.drawable.icon57,				"�������",					6));
		put ("�����",			new IconData(R.drawable.icon58,				"�����",					6));
		put ("�����",			new IconData(R.drawable.icon59,				"�����",					6));
		put ("����",			new IconData(R.drawable.icon60,				"����",					6));
		put ("���",			new IconData(R.drawable.icon61,				"���",					6));
		put ("����",			new IconData(R.drawable.icon61,				"����",					6));
		put ("�����",			new IconData(R.drawable.icon62,				"�����",					6));
		put ("�����",			new IconData(R.drawable.icon63,				"�����",					6));
		put ("��",			new IconData(R.drawable.icon64,				"��",					7));
		put ("���",			new IconData(R.drawable.icon65,				"���",					7));
		put ("����",			new IconData(R.drawable.icon65,				"����",					7));
		put ("������",		new IconData(R.drawable.icon66,				"������",					7));
		put ("�������",		new IconData(R.drawable.icon66,				"�������",				7));
		put ("���",			new IconData(R.drawable.icon67,				"���",					7));
		put ("����",			new IconData(R.drawable.icon68,				"����",					7));
		put ("���",			new IconData(R.drawable.icon69,				"���",					7));
		put ("����",			new IconData(R.drawable.icon69,				"����",					7));
		put ("����",			new IconData(R.drawable.icon70,				"����",					7));
		put ("�����",		new IconData(R.drawable.icon70,				"�����",					7));
		put ("����-��",		new IconData(R.drawable.icon71,				"����-��",				7));
		put ("������",		new IconData(R.drawable.icon72,				"������",					7));
		put ("�����",			new IconData(R.drawable.icon73,				"�����",					7));
		put ("����",			new IconData(R.drawable.icon74,				"����",					7));
		put ("���",			new IconData(R.drawable.icon75,				"���",					7));
		put ("����",			new IconData(R.drawable.icon76,				"����",					8));
		put ("������",		new IconData(R.drawable.icon76,				"������",					8));
		put ("����",			new IconData(R.drawable.icon77,				"����",					8));
		put ("������",		new IconData(R.drawable.icon77,				"������",					8));
		put ("���",			new IconData(R.drawable.icon78,				"���",					8));
		put ("�����",			new IconData(R.drawable.icon78,				"�����",					8));
		put ("���",			new IconData(R.drawable.icon79,				"���",					8));
		put ("���",			new IconData(R.drawable.icon80,				"���",					8));
		put ("�����",			new IconData(R.drawable.icon80,				"�����",					8));
		put ("���",			new IconData(R.drawable.icon81,				"���",					8));
		put ("�����",			new IconData(R.drawable.icon81,				"�����",					8));
		put ("������",		new IconData(R.drawable.icon82,				"������",					8));
		put ("�������",		new IconData(R.drawable.icon82,				"�������",				8));
		put ("�����",			new IconData(R.drawable.icon83,				"�����",					8));
		put ("�������",		new IconData(R.drawable.icon83,				"�������",					8));
		put ("����",			new IconData(R.drawable.icon84,				"����",					8));
		put ("�����",		new IconData(R.drawable.icon84,				"�����",					8));
		put ("����",			new IconData(R.drawable.icon85,				"����",					8));
		put ("������",		new IconData(R.drawable.icon85,				"������",					8));
		put ("����",			new IconData(R.drawable.icon86,				"����",					8));
		put ("�����",			new IconData(R.drawable.icon86,				"�����",					8));
		put ("������",		new IconData(R.drawable.icon87,				"������",					8));
		put ("��������",		new IconData(R.drawable.icon87,				"��������",				8));
		put ("����",		new IconData(R.drawable.icon88,				"����",					8));
		put ("������",		new IconData(R.drawable.icon88,				"������",				8));
		put ("�����",		new IconData(R.drawable.icon89,				"�����",					8));
		put ("�������",		new IconData(R.drawable.icon89,				"�������",				8));
		put ("����",			new IconData(R.drawable.icon90,				"����",					8));
		put ("�����",			new IconData(R.drawable.icon91,				"�����",					8));
		put ("������",		new IconData(R.drawable.icon92,				"������",					8));
		put ("���",			new IconData(R.drawable.icon93,				"���",					8));
		put ("�����",			new IconData(R.drawable.icon93,				"�����",					8));
		put ("���",			new IconData(R.drawable.icon94,				"���",					8));
		put ("���",			new IconData(R.drawable.icon96,				"���",					8));
		put ("�����",			new IconData(R.drawable.icon96,				"�����",					8));
		put ("�����",			new IconData(R.drawable.icon102,			"�����",					9));
		put ("���",			new IconData(R.drawable.icon103,			"���",					9));
		put ("�����",			new IconData(R.drawable.icon104,			"�����",					9));
		put ("������",		new IconData(R.drawable.icon104,			"������",					9));
		put ("���",			new IconData(R.drawable.icon105,			"���",					10));
		put ("�������",		new IconData(R.drawable.icon106,			"�������",				10));
		put ("�����",		new IconData(R.drawable.icon107,			"�����",					10));
		put ("����",			new IconData(R.drawable.icon108,			"����",					10));
		put ("�����",			new IconData(R.drawable.icon109,			"�����",					10));
		put ("�����",		new IconData(R.drawable.icon110,			"�����",					10));
		put ("������",		new IconData(R.drawable.icon111,			"������",					11));
		put ("������",		new IconData(R.drawable.icon112,			"������",					11));
		put ("����",			new IconData(R.drawable.icon113,			"����",					11));
		put ("������",		new IconData(R.drawable.icon114,			"������",					11));
		put ("�������",		new IconData(R.drawable.icon115,			"�������",					11));
		put ("�����",			new IconData(R.drawable.icon116,			"�����",					11));
		put ("����",			new IconData(R.drawable.icon117,			"����",					11));
		put ("�������",		new IconData(R.drawable.icon119,			"�������",				12));
		put ("���",			new IconData(R.drawable.icon120,			"���",					12));
		put ("����",			new IconData(R.drawable.icon121,			"����",					12));
		put ("����",			new IconData(R.drawable.icon122,			"����",					12));
		put ("�������",		new IconData(R.drawable.icon123,			"�������",					12));
		put ("����",			new IconData(R.drawable.icon124,			"����",					13));
		put ("����",			new IconData(R.drawable.icon125,			"����",					13));
		put ("������",		new IconData(R.drawable.icon125,			"������",					13));
		put ("����",			new IconData(R.drawable.icon126,			"����",					13));
		put ("�����",			new IconData(R.drawable.icon126,			"�����",					13));
		put ("����",			new IconData(R.drawable.icon127,			"����",					13));
		put ("����",			new IconData(R.drawable.icon128,			"����",					13));
		put ("����",			new IconData(R.drawable.icon129,			"����",					13));
		put ("���",			new IconData(R.drawable.icon130,			"���",					13));
		put ("����",			new IconData(R.drawable.icon131,			"����",					13));
		put ("���",			new IconData(R.drawable.icon132,			"���",					13));
		put ("�����",			new IconData(R.drawable.icon132,			"�����",					13));
		put ("�����",			new IconData(R.drawable.icon133,			"�����",					13));
		put ("�����",			new IconData(R.drawable.icon134,			"�����",					13));
		put ("������",		new IconData(R.drawable.icon135,			"������",					14));
		put ("������",		new IconData(R.drawable.icon136,			"������",					14));
		put ("�������",		new IconData(R.drawable.icon137,			"�������",				14));
		put ("��",			new IconData(R.drawable.icon138,			"��",					14));
		put ("���",			new IconData(R.drawable.icon139,			"���",					14));
		put ("�����",			new IconData(R.drawable.icon140,			"�����",					14));
		put ("������",			new IconData(R.drawable.icon140,			"������",					14));
		put ("�������",		new IconData(R.drawable.icon141,			"�������",					14));
		put ("������",		new IconData(R.drawable.icon142,			"������",					15));
		put ("����",			new IconData(R.drawable.icon143,			"����",					15));
		put ("��",			new IconData(R.drawable.icon144,			"��",					15));
		put ("����",			new IconData(R.drawable.icon145,			"����",					15));
		put ("����",			new IconData(R.drawable.icon146,			"����",					15));
		put ("�����",		new IconData(R.drawable.icon147,			"�����",					15));
		put ("����",			new IconData(R.drawable.icon148,			"����",					16));
		put ("����",			new IconData(R.drawable.icon149,			"����",					16));
		put ("������",		new IconData(R.drawable.icon150,			"������",					16));
		put ("��������",		new IconData(R.drawable.icon151,			"��������",				16));
		put ("������",			new IconData(R.drawable.icon152,			"������",					16));
		put ("�����",		new IconData(R.drawable.icon153,			"�����",					17));
		put ("��������",		new IconData(R.drawable.icon154,			"��������",				17));
		put ("�����",			new IconData(R.drawable.icon155,			"�����",					17));
		put ("����",			new IconData(R.drawable.icon156,			"����",					17));
		put ("������",			new IconData(R.drawable.icon157,			"������",					17));
		put ("�������",		new IconData(R.drawable.icon158,			"�������",				17));
		put ("���",			new IconData(R.drawable.gym_72,				"���", 				0));
		put ("���",			new IconData(R.drawable.hello_72_2,			"���",				0));
		put ("��������",		new IconData(R.drawable.hospital_72_2,		"��������",			0));
		put ("���",			new IconData(R.drawable.milk_72,			"���",				0));
		put ("�����",			new IconData(R.drawable.time_72,			"�����",				0));
		put ("����",			new IconData(R.drawable.tired_72,			"����",				0));
		put ("�����",			new IconData(R.drawable.tired_72,			"�����",				-100));
		put ("����",			new IconData(R.drawable.egg,				"����",				0));
		put ("�����",		new IconData(R.drawable.haruset,			"�����",				0));
		put ("����",			new IconData(R.drawable.hazeret,			"����",				0));
		put ("����",			new IconData(R.drawable.karpas,				"����",				0));
		put ("����",			new IconData(R.drawable.maror,				"����",				0));
		put ("���",			new IconData(R.drawable.matza,				"���",				0));
		put ("����",			new IconData(R.drawable.matza,				"����",				-100));
		put ("����",			new IconData(R.drawable.zruah,				"����",				0));
		put ("����",			new IconData(R.drawable.finger,				"����",				0));
		put ("������",		new IconData(R.drawable.volleyball,			"������",				0));
		put ("����",			new IconData(R.drawable.pistol,				"����",				0));
		put ("�����",			new IconData(R.drawable.chot,				"�����",				0));
		put ("�����",			new IconData(R.drawable.zombie,				"�����",				0));
		put ("����",			new IconData(R.drawable.star,				"����",				0));
		put ("�������",		new IconData(R.drawable.baseball,			"�������",			0));
		
		put ("�������",		new IconData(R.drawable.synagogue,			"synagogue",		0));
		put ("������",		new IconData(R.drawable.pills,				"������",				0));
		put ("����",			new IconData(R.drawable.mosque,				"����",				0));
		put ("���",			new IconData(R.drawable.monkey,				"���",				0));
		put ("���",			new IconData(R.drawable.elephant,			"���",				0));
		put ("������",		new IconData(R.drawable.condom,				"������",				0));
		put ("�����",			new IconData(R.drawable.church,				"�����",				0));
		put ("����",			new IconData(R.drawable.butterfly,			"����",				0));
		put ("�����",			new IconData(R.drawable.baby,				"�����",				0));
	}};

	/*
	 * the chars that the smile icon can start/end with
	 */
	public static final char iconSmileStartEnd[] = new char[] {
		';',
		':',
		'(',
		')',
		']',
		'[',
		'\\'
	};
}
