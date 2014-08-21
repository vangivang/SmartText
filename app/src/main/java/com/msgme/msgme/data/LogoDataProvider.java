package com.msgme.msgme.data;

import java.util.Hashtable;

import com.msgme.msgme.R;
import com.msgme.msgme.vo.LogoData;


public class LogoDataProvider{

	public static Hashtable<String, LogoData> htLogos = new Hashtable<String, LogoData>() {/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	{
		//	|word to replace								|file name				|map address	|HiegthDiver |WiegthDiveder
        /*				
         *		-=-=-=-=-	International Logos from here	-=-=-=-=-
         */
		put ("abercrombie",			new LogoData(R.drawable.abercrombie_72,			"abercrombie"));
		put ("absolut",				new LogoData(R.drawable.absolut_72,				null));
		put ("americanairlines",	new LogoData(R.drawable.americanairlines_72,	null));
		put ("avis",				new LogoData(R.drawable.avis_72,				"avis"));
		put ("bacardi",				new LogoData(R.drawable.bacardi_128,			null));
		put ("barca",				new LogoData(R.drawable.barcelona_72,			null));
		put ("billabong",			new LogoData(R.drawable.billabong_128,			"billabong",			2.5f,	2.5f));	
		put ("budget",				new LogoData(R.drawable.budget_72,				"budget"));
		put ("bulls",				new LogoData(R.drawable.bulls_72,				null));
		put ("carlsberg",			new LogoData(R.drawable.carlsberg,				null));
		put ("corona",				new LogoData(R.drawable.corona_extra,			null));
		put ("chevrolet",			new LogoData(R.drawable.chevrolet_72,			"chevrolet"));	
		put ("clinique",			new LogoData(R.drawable.clinique_128,			"clinique"));	
		put ("cocacola",			new LogoData(R.drawable.cocacola_128,			null));
		put ("craiser",				new LogoData(R.drawable.craiser_128,			"craiser",			1.8f,	1.8f));
		put ("dietcoke",			new LogoData(R.drawable.dietcoke_72,			null));
		put ("dominos",		    	new LogoData(R.drawable.dominos,	  	    	"dominos"));	
		put ("dhl",		        	new LogoData(R.drawable.dhl,			        "dhl"));	
		put ("diesel",			    new LogoData(R.drawable.diesel,			        null));	
		put ("dkny",				new LogoData(R.drawable.dkny_128,				"dkny"));
		put ("ebay",				new LogoData(R.drawable.ebay_128,				null,		   	2.3f,	2.3f));
		put ("facebook",			new LogoData(R.drawable.facebook_128,			null,			2.5f,	2.5f));	
		put ("finlandia",			new LogoData(R.drawable.finlandia_72,			null));	
		put ("forever21",			new LogoData(R.drawable.forever21_128,			"forever21"));	
		put ("google",				new LogoData(R.drawable.google_128,				null));
		put ("gucci",				new LogoData(R.drawable.gucci_128,				"gucci"));
		put ("guiness",				new LogoData(R.drawable.guiness_72,				null));
		put ("h&m",					new LogoData(R.drawable.hm_72,					"h&m"));
        put ("heineken",			new LogoData(R.drawable.heineken_128,			null));		
        put ("hertz",				new LogoData(R.drawable.hertz_72,				"hertz"));	
        put ("honda",				new LogoData(R.drawable.honda_72,				"honda"));	
        put ("hp",					new LogoData(R.drawable.hp_72,					null));
        put ("ibm",					new LogoData(R.drawable.ibm_72,					null));
        put ("ikea",				new LogoData(R.drawable.ikea_128,				"ikea"));	
        put ("intel",				new LogoData(R.drawable.intel_72,				"intel"));	
        put ("iphone",				new LogoData(R.drawable.iphone_128,				null));	
        put ("jackdaniels",			new LogoData(R.drawable.jackdaniels_72,			null));
		put ("kfc",			        new LogoData(R.drawable.kfc,		        	"kfc"));	
        put ("lakers",				new LogoData(R.drawable.lakers_72,				null));
		put ("loreal",			    new LogoData(R.drawable.loreal,			        "loreal",       2.5f,	2.5f));
		put ("levis",			    new LogoData(R.drawable.levis,		        	"levis",          4f,	4f));	   
        put ("lg",					new LogoData(R.drawable.lg_72,					null));
        put ("mac",					new LogoData(R.drawable.mac_128,				null));
		put ("mtv",			        new LogoData(R.drawable.mtv,			        null));	
        put ("mango",				new LogoData(R.drawable.mango_128,				null));	
        put ("mazda",				new LogoData(R.drawable.mazda_72,				"mazda"));
		put ("nba",			        new LogoData(R.drawable.nba,			        null,             3.5f,  3f));	
		put ("nike",			    new LogoData(R.drawable.nike,			        "nike"));	
		put ("nokia",			    new LogoData(R.drawable.nokia_128,			    "nokia"));	
        put ("ninewest",			new LogoData(R.drawable.ninewest_128,			"ninewest"));
        put ("nissan",				new LogoData(R.drawable.nissan_72,				"nissan"));	
        put ("pepsi",				new LogoData(R.drawable.pepsi_128,				null));	
        put ("prada",				new LogoData(R.drawable.prada_128,				"prada"));	
        put ("realmadrid",			new LogoData(R.drawable.realmadrid_72,			null));		
        put ("redbull",				new LogoData(R.drawable.redbull_128,			null));	
		put ("ripcurl",				new LogoData(R.drawable.ripcurl_128,			null));	
		put ("samsung",				new LogoData(R.drawable.samsung_128,			null));
		put ("smartext",			new LogoData(R.drawable.smartext_128,			null,			2.3f,	2.3f));	
		put ("smirnoff",			new LogoData(R.drawable.smirnoff_128,			null));		
		put ("sony",				new LogoData(R.drawable.sony_128,				null));	
		put ("starbucks",			new LogoData(R.drawable.starbucks_72,			"starbucks"));		
		put ("subway",				new LogoData(R.drawable.subway_128,				"subway"));	
        put ("topshop",				new LogoData(R.drawable.topshop_128,			"topshop"));	
        put ("toyota",				new LogoData(R.drawable.toyota_72,				"toyota"));	
        put ("twitter",				new LogoData(R.drawable.twitter_72,				null));
		put ("virgin",			    new LogoData(R.drawable.virgin,		        	null));	
		put ("vodafone",			new LogoData(R.drawable.vodafone,				"vodafone"));	
        put ("voicom",				new LogoData(R.drawable.voicom_72,				"voicom"));	
        put ("walmart",				new LogoData(R.drawable.walmart_128,			"walmart"));	
        put ("winston",				new LogoData(R.drawable.winston_128,			null));	
        put ("xbox",				new LogoData(R.drawable.xbox_72,				null));
        put ("youtube",				new LogoData(R.drawable.youtube_128,			null));
        put ("zara",				new LogoData(R.drawable.zara_128,				"zara"));
        put ("dodgers",				new LogoData(R.drawable.dodgers,				null));
        put ("galaxy",				new LogoData(R.drawable.galaxy,					null,			1.8f,	1.8f));	
        put ("hellokitty",			new LogoData(R.drawable.hello_kitty,			null));
        put ("xperia",				new LogoData(R.drawable.xperia,					null,			1.8f,	1.8f));	
        put ("yahoo",				new LogoData(R.drawable.yahoo,					null,			1.8f,	1.8f));	
        /*
         * 		-=-=-=-=-	International Logos in hebrew from here	-=-=-=-=-
         */
		put ("אבקרומבי",				new LogoData(R.drawable.abercrombie_72,			"אבקרומבי"));
		put ("נייק",			    	new LogoData(R.drawable.nike,			        null));
		put ("דומינוס",				new LogoData(R.drawable.dominos,			    "דומינוס"));
		put ("קורונה",			    	new LogoData(R.drawable.corona_extra,	    	 null));
		put ("אנביאיי",				new LogoData(R.drawable.nba,	         		null));
		put ("וירגין",			    	new LogoData(R.drawable.virgin,		        	null));
		put ("אמטיוי",			    	new LogoData(R.drawable.mtv,		        	null));
		put ("דיזל",				    new LogoData(R.drawable.diesel,	        		"דיזל"));
		put ("ליוויס",			     	new LogoData(R.drawable.levis,		        	"ליוויס"));
		put ("קרלסברג",				new LogoData(R.drawable.carlsberg,	     		null));
		put ("דיאייצאל",				new LogoData(R.drawable.dhl,		        	"dhl"));
		put ("אבסולוט",				new LogoData(R.drawable.absolut_72,				null));
		put ("נוקיה",			    	new LogoData(R.drawable.nokia_128,		       	"נוקיה"));
		put ("אמריקןאירליינס",			new LogoData(R.drawable.americanairlines_72,	null));
		put ("אויס",					new LogoData(R.drawable.avis_72,				"אויס"));
		put ("בקרדי",					new LogoData(R.drawable.bacardi_128,			null));
		put ("ברסה",					new LogoData(R.drawable.barcelona_72,			null));
		put ("בילבונג",				new LogoData(R.drawable.billabong_128,			"בילבונג",			2.5f,	2.5f));	
		put ("באדגט",				new LogoData(R.drawable.budget_72,				"באדגט"));
		put ("שברולט",				new LogoData(R.drawable.chevrolet_72,			"שברולט"));	
		put ("קליניק",				new LogoData(R.drawable.clinique_128,			null));	
		put ("קוקהקולה",				new LogoData(R.drawable.cocacola_128,			null));	
		put ("קרייזר",					new LogoData(R.drawable.craiser_128,			"קרייזר",			1.8f,	1.8f));
		put ("דיאטקולה",				new LogoData(R.drawable.dietcoke_72,			null));	
		put ("דונהקארן",				new LogoData(R.drawable.dkny_128,				null));
		put ("איביי",					new LogoData(R.drawable.ebay_128,				null,			2.3f,	2.3f));
		put ("פייסבוק",				new LogoData(R.drawable.facebook_128,			null,			2.5f,	2.5f));	
		put ("פינלנדיה",				new LogoData(R.drawable.finlandia_72,			null));	
		put ("פורבר21",				new LogoData(R.drawable.forever21_128,			null));	
		put ("גוגל",					new LogoData(R.drawable.google_128,				null));
		put ("גוצי",					new LogoData(R.drawable.gucci_128,				null));
		put ("גינס",					new LogoData(R.drawable.guiness_72,				null));
		put ("איץאנדאם",				new LogoData(R.drawable.hm_72,					"אייץאנדאם"));
        put ("הייניקן",				new LogoData(R.drawable.heineken_128,			null));		
        put ("הרץ",					new LogoData(R.drawable.hertz_72,				"הרץ"));	
        put ("הונדה",					new LogoData(R.drawable.honda_72,				"הונדה"));	
        put ("אייץפי",					new LogoData(R.drawable.hp_72,					"אייץפי"));
        put ("אייביאם",				new LogoData(R.drawable.ibm_72,					"אייביאם"));
        put ("איקאה",					new LogoData(R.drawable.ikea_128,				"איקאה"));	
        put ("אינטל",					new LogoData(R.drawable.intel_72,				"אינטל"));	
        put ("אייפון",					new LogoData(R.drawable.iphone_128,				null));	
        put ("גקדניאלס",				new LogoData(R.drawable.jackdaniels_72,			null));		
        put ("לייקרס",				new LogoData(R.drawable.lakers_72,				null));
        put ("אלגי",					new LogoData(R.drawable.lg_72,					null));
        put ("מאק",					new LogoData(R.drawable.mac_128,				null));	
        put ("מזדה",					new LogoData(R.drawable.mazda_72,				"מזדה"));	
        put ("ניינווסט",				new LogoData(R.drawable.ninewest_128,			"ניינווסט"));
        put ("ניסאן",					new LogoData(R.drawable.nissan_72,				"ניסאן"));	
        put ("פפסי",					new LogoData(R.drawable.pepsi_128,				null));	
        put ("פראדה",				new LogoData(R.drawable.prada_128,				null));	
        put ("ריאלמדריד",				new LogoData(R.drawable.realmadrid_72,			null));		
        put ("רדבול",					new LogoData(R.drawable.redbull_128,			null));	
		put ("ריפקרל",				new LogoData(R.drawable.ripcurl_128,			null));	
		put ("סמסונג",				new LogoData(R.drawable.samsung_128,			"סמסונג"));
		put ("סמרטקסט",				new LogoData(R.drawable.smartext_128,			null,			2f,	2f));	
		put ("סמירנוף",				new LogoData(R.drawable.smirnoff_128,			null));		
		put ("סוני",					new LogoData(R.drawable.sony_128,				null));	
		put ("סטראבקס",				new LogoData(R.drawable.starbucks_72,			null));		
		put ("סאבווי",				new LogoData(R.drawable.subway_128,				null));	
        put ("טופשופ",				new LogoData(R.drawable.topshop_128,			"טופשופ"));	
        put ("טויוטה",					new LogoData(R.drawable.toyota_72,				"טויוטה"));	
        put ("טוויטר",					new LogoData(R.drawable.twitter_72,				null));
        put ("וויקום",					new LogoData(R.drawable.voicom_72,				null));	
        put ("ווינסטון",				new LogoData(R.drawable.winston_128,			null));	
        put ("אקסבוקס",				new LogoData(R.drawable.xbox_72,				null));
        put ("יוטיוב",					new LogoData(R.drawable.youtube_128,			null));	
        put ("זארה",					new LogoData(R.drawable.zara_128,				null));
        put ("פילאטיס",				new LogoData(R.drawable.pilates29,				"פילאטיס29"));


        
        /*
         *		-=-=-=-=-	Local hebrew Logos from here	-=-=-=-=-
        */   
		put ("אלונית",				new LogoData(R.drawable.alonit_72,				"אלונית"));
		put ("צבא",					new LogoData(R.drawable.army_72,				null));
		put ("ארומה",					new LogoData(R.drawable.aroma_72,				"ארומה"));
		put ("ביתרירושלים",			new LogoData(R.drawable.beitarjerusalem_72,		null));
		put ("קפהקפה",				new LogoData(R.drawable.cafecafe_128,			"קפהקפה"));
		put ("קפהגו",					new LogoData(R.drawable.caffejoe_128,			"קפהגו"));	
		put ("קסטרו",					new LogoData(R.drawable.castro_128,				"קסטרו",			2.3f,	2.3f));
		put ("סלקום",				new LogoData(R.drawable.cellcom_128,			"סלקום"));
		put ("ערוץ2",					new LogoData(R.drawable.channel2_72,			null));	
		put ("דן",					new LogoData(R.drawable.dan_72,					null));	
		put ("דלתא",					new LogoData(R.drawable.delta_128,				"דלתא",			2.3f,	2.3f));	
		put ("דואר",					new LogoData(R.drawable.doar_128,				"דואר ישראל"));	
		put ("אגד",					new LogoData(R.drawable.egged_72,				null));
		put ("אלעל",					new LogoData(R.drawable.elal_128,				null,			2.3f,	2.3f));	
		put ("עלית",					new LogoData(R.drawable.elite_128,				null));	
		put ("פוקס",					new LogoData(R.drawable.fox_72,					"פוקס"));	
		put ("גלגלצ",					new LogoData(R.drawable.galgaltz_72,			null));
		put ("גלי",					new LogoData(R.drawable.gali_128,				"גלי"));
		put ("גולדסטאר",				new LogoData(R.drawable.goldstar_72,			null));
		put ("גולף",					new LogoData(R.drawable.golf_128,				"גולף"));
        put ("הוט",					new LogoData(R.drawable.hot_128,				null));		
        put ("ישראייר",				new LogoData(R.drawable.israir_128,				null,			2.3f,	2.3f));	
        put ("גפניקה",				new LogoData(R.drawable.japanika_128,			"גפניקה",			2,		2));	
        put ("מכביחיפה",				new LogoData(R.drawable.maccabihaifa_72,		null));
        put ("מכביתלאביב",			new LogoData(R.drawable.maccabitelaviv_72,		null));
        put ("מגןדוד",				new LogoData(R.drawable.magendavid_72,			"מגן דוד אדום"));	
        put ("משביר",				new LogoData(R.drawable.mashbir_128,			"משביר לצרכן",			2,		2));	
        put ("אורנג",					new LogoData(R.drawable.orange_72,				"אורנג"));	
        put ("אוסם",					new LogoData(R.drawable.osem_72,				null));		
        put ("פז",					new LogoData(R.drawable.paz_72,					"פז"));	
        put ("פלאפון",				new LogoData(R.drawable.pelephone_72,			"פלאפון"));
        put ("פיצההאט",				new LogoData(R.drawable.pizzahut_72,			"פיצההאט"));
        put ("משטרה",				new LogoData(R.drawable.police_72,				"משטרה"));	
        put ("רכבת",					new LogoData(R.drawable.railway_72,				"רכבת"));	
        put ("רנואר",					new LogoData(R.drawable.renuar_128,				"רנואר",			2,		2));	
        put ("סנו",					new LogoData(R.drawable.sano_72,				null));	
        put ("שטראוס",				new LogoData(R.drawable.shtraus_72,				null));	
        put ("סונול",					new LogoData(R.drawable.sonol_128,				"סונול",			2,		2));	
        put ("ספורט5",				new LogoData(R.drawable.sport5_128,				null,			2,		2));	
		put ("סטימצקי",				new LogoData(R.drawable.stimatzki_128,			"סטימצקי"));	
		put ("סופרפארם",				new LogoData(R.drawable.superpharm_128,			"סופרפארם",			2,		2));	
		put ("טבע",					new LogoData(R.drawable.teva_128,				null));		
		put ("תנובה",					new LogoData(R.drawable.tnuva_72,				null));	
		put ("יד2",					new LogoData(R.drawable.yad2_72,				null));		
		put ("ילו",					new LogoData(R.drawable.yellow_72,				"ילו"));	
        put ("יס",					new LogoData(R.drawable.yes_72,					null));	
        put ("ווינט",					new LogoData(R.drawable.ynet_128,				null));	
	}};

	public static int findItemIndex(String text)
	{
		Object[] htLogoKeys = htLogos.keySet().toArray();
		
		for (int i=0; i<htLogoKeys.length; i++)
		{
			if (((String)htLogoKeys[i]).equals(text) )
				return i;
		}
		
		return -1;
	}
	
	/*
	 * this data container holds the application icons 
	 */
	public static int[] appLogo = new int[] {
		R.drawable.logo_app_72x72_new2				//application and notification icon
	};
}
