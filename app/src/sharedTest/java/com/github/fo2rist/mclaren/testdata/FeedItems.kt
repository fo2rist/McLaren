package com.github.fo2rist.mclaren.testdata

import com.github.fo2rist.mclaren.models.FeedItem
import com.github.fo2rist.mclaren.models.FeedItem.SourceType
import com.github.fo2rist.mclaren.models.FeedItem.Type
import com.github.fo2rist.mclaren.models.ImageUrl
import com.github.fo2rist.mclaren.models.Size
import java.util.*

object FeedItems {
    const val MEDIA_LINK = "http://mclaren.com/formula1"

    private const val ID: Long = 1
    private const val TITLE = "test title"
    private const val CONTENT = "test content"
    private const val SOURCE_NAME_TEST = "test source"
    private const val SOURCE_NAME_MCLAREN = "mclaren.com"
    private val EMPTY_IMAGES = emptyList<ImageUrl>()

    private val GOOGLE_LOGO_IMAGE = ImageUrl.create(
            "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png",
            Size.valueOf(272, 92))
    private val NON_EMPTY_IMAGES = listOf<ImageUrl>(GOOGLE_LOGO_IMAGE, GOOGLE_LOGO_IMAGE)
    private val CONTENT_ARTICLE_HTML_WITH_TABLES = """
        <div>
            <p>
                <strong>Autodromo Nationale Monza, Sunday 3 September</strong>
            </p>\r\n
            <p>
                A gut-wrenchingly disappointing day for the team at Monza. Before the day had even begun, the team discovered an issue with Stoffel’s MGU-K yesterday evening. While changing this element alone within the PU is possible, it’s extremely time-consuming, and due to the parc ferme restrictions the team judged that this would not be possible in the time allotted. Therefore, the team elected to change Stoffel’s engine in its entirety, bringing in a new MGU-K, MGU-H, ICE and turbocharger to the mix, and therefore an associated 25-place grid drop for the race today. This meant Stoffel started in 18th place, just ahead of Fernando in 20th, following his 35-place engine penalty.
            </p>\r\n
            <p>
                At the start, both drivers made excellent progress from their grid slots and quickly made their way through the pack. For Fernando, the progress was short-lived, as he struggled with upshifts due to an issue with the gearbox sensors. He drove an aggressive race despite a car that was difficult to manage, and fought his way up to 11th place before his one and only pit-stop of the race. The gearbox issue progressively worsened and the team lost the ability to monitor it due to the sensor issues, and opted to retire his car on lap 51 as a precaution.
            </p>\r\n
            <p>
                Conversely, Stoffel – who also made a great start – was running strongly from the first lap, making solid progress through the pack, including a decisive move on Sainz. He ran much of his race inside the all-important points-paying top 10, reaching as high as seventh on lap 19. Cruelly he was robbed of any reward for his strong performance, as he reported a loss of power owing to a further suspected MGU-K issue within the new power unit. He retired from the race on lap 34.
            </p>\r\n
            <p> </p>\r\n
            <h2><strong>FERNANDO ALONSO, McLaren Honda Driver, MCL32-05</strong></h2>\r\n
            <table border=\"0\"><tbody><tr><td> </td>\r\n<td>Started </td>\r\n<td>19th </td>\r\n<td> </td>\r\n<td> </td>\r\n</tr><tr><td> </td>\r\n<td>Finished </td>\r\n<td>DNF – gearbox issue (50 laps – classified 17th) </td>\r\n<td> </td>\r\n<td> </td>\r\n</tr><tr><td> </td>\r\n<td>Fastest lap </td>\r\n<td>1m25.871s on lap 44 (13th) </td>\r\n<td> </td>\r\n<td> </td>\r\n</tr><tr><td> </td>\r\n<td>Pitstops</td>\r\n<td>One: lap 30 (3.17s), [Prime/Option]</td>\r\n<td> </td>\r\n<td> </td>\r\n</tr></tbody></table>
            <p>
                “Our bosses were here today and, unfortunately, we could not deliver a good result. It’s very disappointing to have a double DNF.
            </p>\r\n
            <p>
                “I had problems with upshifting from the very early stages of the race, which at some points was costing me a lot of time – almost a second a lap. We tried to fix the issue by changing some settings, but the shifting never worked as it should have, and it hampered my race.
            </p>\r\n
            <p>
                “Starting from the back of the grid was never going to be easy here. We made up a few places during the race, but there was little chance we could make it into the points today.
            </p>\r\n
            <p>
                “Now we are looking forward to Singapore, which is a more suitable track for us.”
            </p>\r\n
            <p> </p>\r\n
            <h2><strong>STOFFEL VANDOORNE, McLaren Honda Driver, MCL32-04</strong></h2>\r\n
            <table border=\"0\"><tbody><tr><td> </td>\r\n<td>Started </td>\r\n<td>18th</td>\r\n<td> </td>\r\n<td> </td>\r\n</tr><tr><td> </td>\r\n<td>Finished </td>\r\n<td>DNF – PU issue (33 laps)</td>\r\n<td> </td>\r\n<td> </td>\r\n</tr><tr><td> </td>\r\n<td>Fastest lap </td>\r\n<td>1m26.912s on lap 30 (19th)  </td>\r\n<td> </td>\r\n<td> </td>\r\n</tr><tr><td> </td>\r\n<td>Pitstops</td>\r\n<td>-</td>\r\n<td> </td>\r\n<td> </td>\r\n</tr></tbody></table>
            <p>
                “We only knew on the lap that I retired that there was any sign of a problem, as I lost power. It’s a similar issue to yesterday, and it’s a shame because we changed the engine overnight for a brand new one today. To have another problem in a race which was going very well is obviously frustrating. Hopefully it will be better in Singapore.
            </p>\r\n
            <p>
                “It’s pretty difficult to draw positives from a weekend like this. From my side, it had actually been a really positive weekend in terms of my driving and the performance I’ve put in – it’s been very strong. The last few races have been very strong for me, in fact. It’s just such a shame to finish with another retirement, and not have any reward for all of that. And we’ve had another issue today, but we have to move on.
            </p>\r\n
            <p>
                “I guess it’s possible I’ll have another grid drop in Singapore, although we don’t yet know exactly what the issue was today, despite it looking like a similar problem. We’ll have to wait and see.”
            </p>\r\n
            <p> </p>\r\n
            <h2><strong>ERIC BOULLIER, McLaren Honda Racing Director</strong></h2>\r\n
            <p>
                “Today, as we have seen so many times this season, the talent of our drivers shone, and we held onto hope that we would be able to achieve a positive result against the odds this afternoon. Once again, we were left dejected and dissatisfied. Both drivers made excellent starts and held their own in the pack for as long as they possibly could, on a track where we knew we’d be facing a tough challenge. By the end of lap six, Stoffel and Fernando were sitting in 13th and 14th positions respectively, and began progressively pushing forwards as other cars began to pit.
            </p>\r\n
            <p>
                “Only a few laps into the race, Fernando began to struggle with gearbox issues, which we suspect derived from sensor problems. Although his engineers worked hard throughout the race to instruct Fernando with software management tools to try to rectify the issue, it became more and more difficult to monitor the gearbox remotely due to the sensor failures, so we had no choice but to retire the car as a precaution. Fernando had been on the back foot for most of the race, and had found it tough to maintain pace and momentum in a car that was tough to manage. Under the circumstances, he drove an excellent, very spirited race fraught with challenges, and it’s a shame we couldn’t get him to the flag.
            </p>\r\n
            <p>
                “For Stoffel, his day ended with heartbreak. His performance all weekend has been stellar, and this afternoon he was running in the top 10 for the duration of his race – at one point as high as seventh from 18th on the grid. It’s both frustrating and a huge shame that once again engine reliability issues have meant that he was not only forced to waste the opportunity to start the race in eighth place on the grid, but that all the hard work he would ultimately put in to make progress through the pack and aim for points would be rendered pointless. Like yesterday in Q3, he lost power with what we suspect is the same issue as in qualifying, and he had to retire the car.
            </p>\r\n
            <p>
                “For the whole team – who have all worked so hard to give us a fighting chance on this most challenging of tracks – it’s an utterly frustrating and disappointing way to end our Italian Grand Prix weekend and the European season.”
            </p>\r\n
            <p> </p>\r\n
            <h2><strong>YUSUKE HASEGAWA, Honda R&amp;D Co. Ltd Head of F1 Project &amp; Executive Chief Engineer </strong></h2>\r\n
            <p>
                “We had a beautiful Italian blue sky today, the exact opposite of yesterday’s cold and rainy day. Unfortunately, the on-track action didn’t improve for us and the race turned out to be extremely disappointing.
            </p>\r\n
            <p>
                “The day started with a PU change on Stoffel’s car as a result of the MGU-K issue in yesterday’s qualifying session. This meant Stoffel started the race from the back of grid in P18 alongside Fernando. Despite the tough circumstances, Stoffel had a good start and was having a strong race within the top ten before his retirement. Unfortunately, he lost power supply from his PU and we’re investigating the cause of the issue.
            </p>\r\n
            <p>
                “Fernando also started the race from back of the grid. He showed a consistent pace during the race, but the team eventually had to retire his car as the ability to monitor his gearbox was lost.
            </p>\r\n
            <p>
                “Although it’s disappointing we were unable to finish the race, it’s still slightly positive that we showed good pace here in Monza, even though this is one of the more challenging circuits on the calendar for us.
            </p>\r\n
            <p>
                “The next race in Singapore is on a circuit that suits the characteristics of our car, so we will focus there on pushing for much-needed championship points.”
                <br>
            </p>
        </div>""".trimIndent()
    private val CONTENT_ARTICLE_HTML_WITH_LINK = """
        <div>\r\n
        <p>
            Join us for the fourth and final round of the World's Fastest Gamer rFactor 2 qualifiers at Silverstone. Virtual racers will take the wheel of a McLaren 650S GT3 to compete for the ultimate job in esports, the chance to become one of McLaren's official simulator drivers.
        </p>\r\n
        <p>
            Watch here: <a href=\"https://www.youtube.com/watch?v=syBMTAXiidk&utm_source=Twitter&utm_medium=Social&utm_campaign=WFG\" target=\"_blank\">https://www.youtube.com/watch?v=syBMTAXiidk&utm_source=Twitter&utm_medium=Social&utm_campaign=WFG</a>
        </p>\r\n
        </div>""".trimIndent()


    @JvmField
    val TWITTER_GALLERY_ITEM = FeedItem(
            ID,
            Type.Gallery,
            TITLE,
            CONTENT,
            Date(),
            SourceType.Twitter,
            SOURCE_NAME_TEST,
            MEDIA_LINK,
            EMPTY_IMAGES)

    @JvmField
    val INSTAGRAM_GALLERY_ITEM = FeedItem(
            ID,
            Type.Gallery,
            TITLE,
            CONTENT,
            Date(),
            SourceType.Instagram,
            SOURCE_NAME_TEST,
            MEDIA_LINK,
            EMPTY_IMAGES)

    @JvmField
    val ARTICLE_ITEM_WITH_LINKS = FeedItem(
            ID,
            Type.Article,
            TITLE,
            CONTENT_ARTICLE_HTML_WITH_LINK,
            Date(),
            SourceType.Unknown,
            SOURCE_NAME_MCLAREN,
            MEDIA_LINK,
            NON_EMPTY_IMAGES)

    @JvmField
    val ARTICLE_ITEM_WITH_TABLES = ARTICLE_ITEM_WITH_LINKS.copy(content = CONTENT_ARTICLE_HTML_WITH_TABLES)

    @JvmField
    val VIDEO_ITEM = FeedItem(
            ID,
            Type.Video,
            TITLE,
            CONTENT,
            Date(),
            SourceType.Twitter,
            SOURCE_NAME_MCLAREN,
            MEDIA_LINK)
}
