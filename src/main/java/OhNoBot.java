import com.aspose.ocr.AsposeOCR;
import java.awt.*;
import java.awt.event.InputEvent;
import java.io.File;
import java.io.IOException;
import java.awt.image.*;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.History;
import org.alicebot.ab.MagicBooleans;
import org.alicebot.ab.MagicStrings;

public class OhNoBot {

    private static String command = "";

    private static String previousMessage = "";

    private static final boolean TRACE_MODE = false;

    public static void main(String[] args) throws IllegalArgumentException {

        AsposeOCR api = new AsposeOCR();

        // Recognize page from BufferedImage
        try {
            boolean goUp = false;
            boolean goDown = false;
            boolean goRight = false;
            boolean goLeft = false;

            while (!command.startsWith("stop")) {

                command = "";

                Robot bot = new Robot();

                // only looks at the bottom line of chat, width of the chatbox
                Rectangle capture = new Rectangle(568, 25);
                capture.setLocation(0, 260); //40 for w/o username


                BufferedImage input = bot.createScreenCapture(capture);


                boolean isCool = false;
                boolean isAdmin = false;
                boolean isBlacklisted = false;

                //this lets you check the color of the player's name (or whatever is on screen), and adds/remove cmds if needed
                for (int i = 0; i < input.getWidth(); i++) {
                    for (int j = 0; j < input.getHeight(); j++) {
                        int r = (input.getRGB(i, j) >> 16) & 0xFF;
                        int g = (input.getRGB(i, j) >> 8) & 0xFF;
                        int b = (input.getRGB(i, j)) & 0xFF;
                        if (r == 177 && g == 167 && b == 255) {
                            isCool = true;
                            break;
                        } else if (r == 245 && g == 205 && b == 48) {
                            isAdmin = true;
                            break;
                        } else if (r == 117 && g == 0 && b == 0) {
                            isBlacklisted = true;
                            break;
                        }
                    }
                }



                if (!isBlacklisted) {

                    //turns image into black text on pure white
                    for (int i = 0; i < input.getWidth(); i++) {
                        for (int j = 0; j < input.getHeight(); j++) {
                            int r = (input.getRGB(i, j) >> 16) & 0xFF;
                            int g = (input.getRGB(i, j) >> 8) & 0xFF;
                            int b = (input.getRGB(i, j)) & 0xFF;
                            if (r >= 200 && g >= 200 && b >= 200) {
                                input.setRGB(i, j, Color.black.getRGB());
                            } else {
                                input.setRGB(i, j, Color.white.getRGB());
                            }
                        }
                    }

                    String result = api.RecognizePage(input);

                    // makes sure not to repeat cmds that is just left in chat
                    if (!result.equals(previousMessage)) {
                        previousMessage = result;
                        System.out.println("Result BufferedImage: " + result);

                        // finds the cmd character (default -) and remove username and the -
                        for (int i = 0; i < result.length(); i++) {
                            if (result.charAt(i) == '-') {
                                command = result.substring(i + 1);
                                break;
                            }
                        }


                        // all the normal cmds
                        if (command.startsWith("up")) {
                            bot.keyPress(87);
                            bot.delay(500);
                            bot.keyRelease(87);
                        } else if (command.startsWith("lup")) {
                            bot.keyPress(87);
                            bot.delay(200);
                            bot.keyRelease(87);
                        } else if (command.startsWith("hlup")) {
                            bot.keyPress(87);
                            bot.delay(750);
                            bot.keyRelease(87);
                        } else if (command.startsWith("sup")) {
                            if (!goUp) {
                                bot.keyPress(87);
                                goUp = true;
                            }
                            else {
                                bot.keyRelease(87);
                                goUp = false;
                            }
                        } else if (command.startsWith("down")) {
                            bot.keyPress(83);
                            bot.delay(500);
                            bot.keyRelease(83);
                        } else if (command.startsWith("ldown")) {
                            bot.keyPress(83);
                            bot.delay(200);
                            bot.keyRelease(83);
                        } else if (command.startsWith("hdown")) {
                            bot.keyPress(83);
                            bot.delay(750);
                            bot.keyRelease(83);
                        } else if (command.startsWith("sdown")) {
                            if (!goDown) {
                                bot.keyPress(83);
                                goDown = true;
                            }
                            else {
                                bot.keyRelease(83);
                                goDown = false;
                            }
                        }  else if (command.startsWith("left")) {
                            bot.keyPress(65);
                            bot.delay(500);
                            bot.keyRelease(65);
                        } else if (command.startsWith("lleft")) {
                            bot.keyPress(65);
                            bot.delay(200);
                            bot.keyRelease(65);
                        } else if (command.startsWith("hleft")) {
                            bot.keyPress(65);
                            bot.delay(750);
                            bot.keyRelease(65);
                        } else if (command.startsWith("sleft")) {
                            if (!goLeft) {
                                bot.keyPress(65);
                                goLeft = true;
                            }
                            else {
                                bot.keyRelease(65);
                                goLeft = false;
                            }
                        }  else if (command.startsWith("righ")) {
                            bot.keyPress(68);
                            bot.delay(500);
                            bot.keyRelease(68);
                        } else if (command.startsWith("lrigh")) {
                            bot.keyPress(68);
                            bot.delay(200);
                            bot.keyRelease(68);
                        } else if (command.startsWith("hrigh")) {
                            bot.keyPress(68);
                            bot.delay(760);
                            bot.keyRelease(68);
                        } else if (command.startsWith("srigh")) {
                            if (!goRight) {
                                bot.keyPress(68);
                                goRight = true;
                            }
                            else {
                                bot.keyRelease(68);
                                goRight = false;
                            }
                        } else if (command.startsWith("jump")) {
                            bot.keyPress(32);
                            bot.delay(10);
                            bot.keyRelease(32);
                        } else if (command.startsWith("uump")) {
                            bot.keyPress(32);
                            bot.keyPress(87);
                            bot.delay(10);
                            bot.keyRelease(32);
                            bot.delay(490);
                            bot.keyRelease(87);
                        } else if (command.startsWith("luump")) {
                            bot.keyPress(32);
                            bot.keyPress(87);
                            bot.delay(10);
                            bot.keyRelease(32);
                            bot.delay(190);
                            bot.keyRelease(87);
                        } else if (command.startsWith("huump")) {
                            bot.keyPress(32);
                            bot.keyPress(87);
                            bot.delay(10);
                            bot.keyRelease(32);
                            bot.delay(740);
                            bot.keyRelease(87);
                        } else if (command.startsWith("dump")) {
                            bot.keyPress(32);
                            bot.keyPress(83);
                            bot.delay(10);
                            bot.keyRelease(32);
                            bot.delay(490);
                            bot.keyRelease(83);
                        } else if (command.startsWith("ldump")) {
                            bot.keyPress(32);
                            bot.keyPress(83);
                            bot.delay(10);
                            bot.keyRelease(32);
                            bot.delay(190);
                            bot.keyRelease(83);
                        } else if (command.startsWith("hdump")) {
                            bot.keyPress(32);
                            bot.keyPress(83);
                            bot.delay(10);
                            bot.keyRelease(32);
                            bot.delay(740);
                            bot.keyRelease(83);
                        } else if (command.startsWith("rump")) {
                            bot.keyPress(32);
                            bot.keyPress(68);
                            bot.delay(10);
                            bot.keyRelease(32);
                            bot.delay(490);
                            bot.keyRelease(68);
                        } else if (command.startsWith("lrump")) {
                            bot.keyPress(32);
                            bot.keyPress(68);
                            bot.delay(10);
                            bot.keyRelease(32);
                            bot.delay(190);
                            bot.keyRelease(68);
                        } else if (command.startsWith("hrump")) {
                            bot.keyPress(32);
                            bot.keyPress(68);
                            bot.delay(10);
                            bot.keyRelease(32);
                            bot.delay(740);
                            bot.keyRelease(68);
                        } else if (command.startsWith("lump")) {
                            bot.keyPress(32);
                            bot.keyPress(65);
                            bot.delay(10);
                            bot.keyRelease(32);
                            bot.delay(490);
                            bot.keyRelease(65);
                        } else if (command.startsWith("llump")) {
                            bot.keyPress(32);
                            bot.keyPress(65);
                            bot.delay(10);
                            bot.keyRelease(32);
                            bot.delay(190);
                            bot.keyRelease(65);
                        } else if (command.startsWith("hlump")) {
                            bot.keyPress(32);
                            bot.keyPress(65);
                            bot.delay(10);
                            bot.keyRelease(32);
                            bot.delay(740);
                            bot.keyRelease(65);
                        } else if (command.startsWith("ball")) {
                            bot.keyPress(49);
                            bot.delay(10);
                            bot.keyRelease(49);
                            bot.delay(10);
                            bot.mousePress(InputEvent.BUTTON1_MASK);
                            bot.delay(10);
                            bot.mouseRelease(InputEvent.BUTTON1_MASK);
                            bot.delay(10);
                            bot.keyPress(49);
                            bot.delay(10);
                            bot.keyRelease(49);
                        } else if (command.startsWith("trip")) {
                            bot.keyPress(50);
                            bot.delay(10);
                            bot.keyRelease(50);
                            bot.delay(10);
                            bot.mousePress(InputEvent.BUTTON1_MASK);
                            bot.delay(10);
                            bot.mouseRelease(InputEvent.BUTTON1_MASK);
                            bot.delay(10);
                            bot.keyPress(50);
                            bot.delay(10);
                            bot.keyRelease(50);
                        } else if (command.startsWith("sword")) {
                            bot.keyPress(51);
                            bot.delay(10);
                            bot.keyRelease(51);
                        } else if (command.startsWith("click")) {
                            bot.mousePress(InputEvent.BUTTON1_MASK);
                            bot.delay(10);
                            bot.mouseRelease(InputEvent.BUTTON1_MASK);
                        } else if (command.startsWith("slck")) {
                            bot.keyPress(16);
                            bot.delay(10);
                            bot.keyRelease(16);
                        } else if (command.startsWith("dab")) {
                            bot.keyPress(52);
                            bot.delay(10);
                            bot.keyRelease(52);
                            bot.delay(10);
                            bot.mousePress(InputEvent.BUTTON1_MASK);
                            bot.delay(10);
                            bot.mouseRelease(InputEvent.BUTTON1_MASK);
                            bot.delay(10);
                            bot.keyPress(52);
                            bot.delay(10);
                            bot.keyRelease(52);
                        } else if (command.startsWith("fly")) {
                            bot.keyPress(53);
                            bot.delay(10);
                            bot.keyRelease(53);
                            bot.delay(10);
                            bot.mousePress(InputEvent.BUTTON1_MASK);
                            bot.delay(10);
                            bot.mouseRelease(InputEvent.BUTTON1_MASK);
                            bot.delay(10);
                            bot.keyPress(53);
                            bot.delay(10);
                            bot.keyRelease(53);
                        } else if (command.startsWith("ask")) {
                            bot.keyPress(47);
                            bot.delay(1);
                            bot.keyRelease(47);
                            bot.delay(10);
                            type(bot, "Bot: " + chatBot(findString(command)));
                            bot.keyPress(10);
                            bot.delay(1);
                            bot.keyRelease(10);
                        } else if (command.startsWith("furry")) {
                            bot.keyPress(47);
                            bot.delay(1);
                            bot.keyRelease(47);
                            bot.delay(10);
                            int random = (int) (Math.random() * 16);
                            String cmd = "";
                            if (random == 0) {
                                cmd = "OwO";
                            } else if (random == 1) {
                                cmd = "UwU";
                            } else if (random == 2) {
                                cmd = "VwV";
                            } else if (random == 3) {
                                cmd = ">w<";
                            } else if (random == 4) {
                                cmd = "x3";
                            } else if (random == 5) {
                                cmd = "OvO";
                            } else if (random == 6) {
                                cmd = "UvU";
                            } else if (random == 7) {
                                cmd = "-w-";
                            } else if (random == 8) {
                                cmd = ":3";
                            } else if (random == 9) {
                                cmd = "*nuzzles*";
                            } else if (random == 10) {
                                cmd = "*rawr*";
                            } else if (random == 11) {
                                cmd = "hsss...";
                            } else if (random == 12) {
                                cmd = "*pounces*";
                            } else if (random == 13) {
                                cmd = "*grrr*";
                            } else if (random == 14) {
                                cmd = "*meow*";
                            } else {
                                cmd = ">.<";
                            }
                            type(bot, cmd);
                            bot.keyPress(10);
                            bot.delay(1);
                            bot.keyRelease(10);
                        }

                        // higher level cmds
                        if (isCool) {
                            if (command.startsWith("ptrip")) {
                                bot.keyPress(47);
                                bot.delay(1);
                                bot.keyRelease(47);
                                bot.delay(10);
                                String cmd = "!trip " + findString(command);
                                type(bot, cmd);
                                bot.keyPress(10);
                                bot.delay(1);
                                bot.keyRelease(10);
                            } else if (command.startsWith("pball")) {
                                bot.keyPress(47);
                                bot.delay(1);
                                bot.keyRelease(47);
                                bot.delay(10);
                                String cmd = "!ball " + findString(command);
                                type(bot, cmd);
                                bot.keyPress(10);
                                bot.delay(1);
                                bot.keyRelease(10);
                            } else if (command.startsWith("pkill")) {
                                bot.keyPress(47);
                                bot.delay(1);
                                bot.keyRelease(47);
                                bot.delay(10);
                                String cmd = "!kill " + findString(command);
                                type(bot, cmd);
                                bot.keyPress(10);
                                bot.delay(1);
                                bot.keyRelease(10);
                            } else if (command.startsWith("pflng")) {
                                bot.keyPress(47);
                                bot.delay(1);
                                bot.keyRelease(47);
                                bot.delay(10);
                                String cmd = "!fling " + findString(command);
                                type(bot, cmd);
                                bot.keyPress(10);
                                bot.delay(1);
                                bot.keyRelease(10);
                            } else if (command.startsWith("pflkl")) {
                                bot.keyPress(47);
                                bot.delay(1);
                                bot.keyRelease(47);
                                bot.delay(10);
                                String cmd = "!flingkill " + findString(command);
                                type(bot, cmd);
                                bot.keyPress(10);
                                bot.delay(1);
                                bot.keyRelease(10);
                            } else if (command.startsWith("say")) {
                                bot.keyPress(47);
                                bot.delay(1);
                                bot.keyRelease(47);
                                bot.delay(10);
                                String cmd = "Oh no, " + findString(command);
                                type(bot, cmd);
                                bot.keyPress(10);
                                bot.delay(1);
                                bot.keyRelease(10);
                            }
                        }

                        //higher level cmds, ban + lame are world-specific
                        if (isAdmin) {
                            if (command.startsWith("blacklist")) {
                                bot.keyPress(47);
                                bot.delay(1);
                                bot.keyRelease(47);
                                bot.delay(10);
                                String cmd = "!team set " + findString(command) + " maroon  ";
                                type(bot, cmd);
                                bot.keyPress(10);
                                bot.delay(1);
                                bot.keyRelease(10);
                                bot.delay(100);
                                bot.keyPress(47);
                                bot.delay(1);
                                bot.keyRelease(47);
                                bot.delay(10);
                                cmd = "!kill " + findString(command);
                                type(bot, cmd);
                                bot.keyPress(10);
                                bot.delay(1);
                                bot.keyRelease(10);
                            } else if (command.startsWith("unblacklist")) {
                                bot.keyPress(47);
                                bot.delay(1);
                                bot.keyRelease(47);
                                bot.delay(10);
                                String cmd = "!team set " + findString(command) + " sea green  ";
                                type(bot, cmd);
                                bot.keyPress(10);
                                bot.delay(1);
                                bot.keyRelease(10);
                                bot.delay(100);
                                bot.keyPress(47);
                                bot.delay(1);
                                bot.keyRelease(47);
                                bot.delay(10);
                                cmd = "!kill " + findString(command);
                                type(bot, cmd);
                                bot.keyPress(10);
                                bot.delay(1);
                                bot.keyRelease(10);
                            } else if (command.startsWith("kick")) {
                                bot.keyPress(47);
                                bot.delay(1);
                                bot.keyRelease(47);
                                bot.delay(10);
                                String cmd = "!hub " + findString(command);
                                type(bot, cmd);
                                bot.keyPress(10);
                                bot.delay(1);
                                bot.keyRelease(10);
                            } else if (command.startsWith("shout")) {
                                bot.keyPress(47);
                                bot.delay(1);
                                bot.keyRelease(47);
                                bot.delay(10);
                                String cmd = "!shout " + findString(command);
                                type(bot, cmd);
                                bot.keyPress(10);
                                bot.delay(1);
                                bot.keyRelease(10);
                            } else if (command.startsWith("lame")) {
                                bot.keyPress(47);
                                bot.delay(1);
                                bot.keyRelease(47);
                                bot.delay(10);
                                String cmd = "!team set " + findString(command) + " magenta  ";
                                type(bot, cmd);
                                bot.keyPress(10);
                                bot.delay(1);
                                bot.keyRelease(10);
                                bot.delay(100);
                                bot.keyPress(47);
                                bot.delay(1);
                                bot.keyRelease(47);
                                bot.delay(10);
                                cmd = "!kill " + findString(command);
                                type(bot, cmd);
                                bot.keyPress(10);
                                bot.delay(1);
                                bot.keyRelease(10);
                            } else if (command.startsWith("pfly")) {
                                bot.keyPress(47);
                                bot.delay(1);
                                bot.keyRelease(47);
                                bot.delay(10);
                                String cmd = "!fly " + findString(command);
                                type(bot, cmd);
                                bot.keyPress(10);
                                bot.delay(1);
                                bot.keyRelease(10);
                            } else if (command.startsWith("pufly")) {
                                bot.keyPress(47);
                                bot.delay(1);
                                bot.keyRelease(47);
                                bot.delay(10);
                                String cmd = "!unfly " + findString(command);
                                type(bot, cmd);
                                bot.keyPress(10);
                                bot.delay(1);
                                bot.keyRelease(10);
                            }
                        }

                    }
                }
                bot.delay(250);

            }
        } catch (IOException | AWTException | SecurityException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    // finds all characters between two more ampersands (ex. &ban &username&, it will find "username")
    public static String findString(String input) {
        boolean foundEnd = false;
        int start = 0;
        int end = input.length();
        for (int i = 1; i < input.length() - 1; i++) {
            if (start == 0 && input.charAt(i) == '-') {
                start = i + 1;
            } else if (start != 0 && input.charAt(i) == '-' && input.charAt(i-1) != '\\') {
                end = i;
                foundEnd = true;
                break;
            }
        }
        String output = "";
        if (foundEnd)
            output = input.substring(start, end);
        else
            output = "error";
        if (output.contains("Trial Licenses")) {
            output = output.substring(0, output.length() - " ************* Trial Licenses ".length());
        }
        return output;
    }

    // uses robot to type letters
    public static void type(Robot bot, String cmd) {
        for (int i = 0; i < cmd.length(); i++) {
            if (!cmd.substring(i, i + 1).toLowerCase().equals(cmd.substring(i, i + 1))) {
                bot.keyPress(16);
            }

            if (cmd.substring(i, i + 1).equalsIgnoreCase("a")) {
                bot.keyPress(65);
                bot.delay(1);
                bot.keyRelease(65);
            } else if (cmd.substring(i, i + 1).equalsIgnoreCase("b")) {
                bot.keyPress(66);
                bot.delay(1);
                bot.keyRelease(66);
            } else if (cmd.substring(i, i + 1).equalsIgnoreCase("c")) {
                bot.keyPress(67);
                bot.delay(1);
                bot.keyRelease(67);
            } else if (cmd.substring(i, i + 1).equalsIgnoreCase("d")) {
                bot.keyPress(68);
                bot.delay(1);
                bot.keyRelease(68);
            } else if (cmd.substring(i, i + 1).equalsIgnoreCase("e")) {
                bot.keyPress(69);
                bot.delay(1);
                bot.keyRelease(69);
            } else if (cmd.substring(i, i + 1).equalsIgnoreCase("f")) {
                bot.keyPress(70);
                bot.delay(1);
                bot.keyRelease(70);
            } else if (cmd.substring(i, i + 1).equalsIgnoreCase("g")) {
                bot.keyPress(71);
                bot.delay(1);
                bot.keyRelease(71);
            } else if (cmd.substring(i, i + 1).equalsIgnoreCase("h")) {
                bot.keyPress(72);
                bot.delay(1);
                bot.keyRelease(72);
            } else if (cmd.substring(i, i + 1).equalsIgnoreCase("i")) {
                bot.keyPress(73);
                bot.delay(1);
                bot.keyRelease(73);
            } else if (cmd.substring(i, i + 1).equalsIgnoreCase("j")) {
                bot.keyPress(74);
                bot.delay(1);
                bot.keyRelease(74);
            } else if (cmd.substring(i, i + 1).equalsIgnoreCase("k")) {
                bot.keyPress(75);
                bot.delay(1);
                bot.keyRelease(75);
            } else if (cmd.substring(i, i + 1).equalsIgnoreCase("l")) {
                bot.keyPress(76);
                bot.delay(1);
                bot.keyRelease(76);
            } else if (cmd.substring(i, i + 1).equalsIgnoreCase("m")) {
                bot.keyPress(77);
                bot.delay(1);
                bot.keyRelease(77);
            } else if (cmd.substring(i, i + 1).equalsIgnoreCase("n")) {
                bot.keyPress(78);
                bot.delay(1);
                bot.keyRelease(78);
            } else if (cmd.substring(i, i + 1).equalsIgnoreCase("o")) {
                bot.keyPress(79);
                bot.delay(1);
                bot.keyRelease(79);
            } else if (cmd.substring(i, i + 1).equalsIgnoreCase("p")) {
                bot.keyPress(80);
                bot.delay(1);
                bot.keyRelease(80);
            } else if (cmd.substring(i, i + 1).equalsIgnoreCase("q")) {
                bot.keyPress(81);
                bot.delay(1);
                bot.keyRelease(81);
            } else if (cmd.substring(i, i + 1).equalsIgnoreCase("r")) {
                bot.keyPress(82);
                bot.delay(1);
                bot.keyRelease(82);
            } else if (cmd.substring(i, i + 1).equalsIgnoreCase("s")) {
                bot.keyPress(83);
                bot.delay(1);
                bot.keyRelease(83);
            } else if (cmd.substring(i, i + 1).equalsIgnoreCase("t")) {
                bot.keyPress(84);
                bot.delay(1);
                bot.keyRelease(84);
            } else if (cmd.substring(i, i + 1).equalsIgnoreCase("u")) {
                bot.keyPress(85);
                bot.delay(1);
                bot.keyRelease(85);
            } else if (cmd.substring(i, i + 1).equalsIgnoreCase("v")) {
                bot.keyPress(86);
                bot.delay(1);
                bot.keyRelease(86);
            } else if (cmd.substring(i, i + 1).equalsIgnoreCase("w")) {
                bot.keyPress(87);
                bot.delay(1);
                bot.keyRelease(87);
            } else if (cmd.substring(i, i + 1).equalsIgnoreCase("x")) {
                bot.keyPress(88);
                bot.delay(1);
                bot.keyRelease(88);
            } else if (cmd.substring(i, i + 1).equalsIgnoreCase("y")) {
                bot.keyPress(89);
                bot.delay(1);
                bot.keyRelease(89);
            } else if (cmd.substring(i, i + 1).equalsIgnoreCase("z")) {
                bot.keyPress(90);
                bot.delay(1);
                bot.keyRelease(90);
            } else if (cmd.substring(i, i + 1).equalsIgnoreCase("0")) {
                bot.keyPress(48);
                bot.delay(1);
                bot.keyRelease(48);
            } else if (cmd.substring(i, i + 1).equalsIgnoreCase("1")) {
                bot.keyPress(49);
                bot.delay(1);
                bot.keyRelease(49);
            } else if (cmd.substring(i, i + 1).equalsIgnoreCase("2")) {
                bot.keyPress(50);
                bot.delay(1);
                bot.keyRelease(50);
            } else if (cmd.substring(i, i + 1).equalsIgnoreCase("3")) {
                bot.keyPress(51);
                bot.delay(1);
                bot.keyRelease(51);
            } else if (cmd.substring(i, i + 1).equalsIgnoreCase("4")) {
                bot.keyPress(52);
                bot.delay(1);
                bot.keyRelease(52);
            } else if (cmd.substring(i, i + 1).equalsIgnoreCase("5")) {
                bot.keyPress(53);
                bot.delay(1);
                bot.keyRelease(53);
            } else if (cmd.substring(i, i + 1).equalsIgnoreCase("6")) {
                bot.keyPress(54);
                bot.delay(1);
                bot.keyRelease(54);
            } else if (cmd.substring(i, i + 1).equalsIgnoreCase("7")) {
                bot.keyPress(55);
                bot.delay(1);
                bot.keyRelease(55);
            } else if (cmd.substring(i, i + 1).equalsIgnoreCase("8")) {
                bot.keyPress(56);
                bot.delay(1);
                bot.keyRelease(56);
            } else if (cmd.substring(i, i + 1).equalsIgnoreCase("9")) {
                bot.keyPress(57);
                bot.delay(1);
                bot.keyRelease(57);
            } else if (cmd.substring(i, i + 1).equalsIgnoreCase("_")) {
                bot.keyPress(16);
                bot.delay(10);
                bot.keyPress(45);
                bot.delay(1);
                bot.keyRelease(45);
                bot.keyRelease(16);
            } else if (cmd.substring(i, i + 1).equalsIgnoreCase("!")) {
                bot.keyPress(16);
                bot.delay(10);
                bot.keyPress(49);
                bot.delay(1);
                bot.keyRelease(49);
                bot.keyRelease(16);
            } else if (cmd.substring(i, i + 1).equalsIgnoreCase(" ")) {
                bot.keyPress(32);
                bot.delay(1);
                bot.keyRelease(32);
            } else if (cmd.substring(i, i + 1).equalsIgnoreCase(",")) {
                bot.keyPress(44);
                bot.delay(1);
                bot.keyRelease(44);
            } else if (cmd.substring(i, i + 1).equalsIgnoreCase(".")) {
                bot.keyPress(46);
                bot.delay(1);
                bot.keyRelease(46);
            } else if (cmd.substring(i, i + 1).equalsIgnoreCase("?")) {
                bot.keyPress(16);
                bot.delay(10);
                bot.keyPress(47);
                bot.delay(1);
                bot.keyRelease(47);
                bot.keyRelease(16);
            } else if (cmd.substring(i, i + 1).equalsIgnoreCase("/")) {
                bot.keyPress(47);
                bot.delay(1);
                bot.keyRelease(47);
            } else if (cmd.substring(i, i + 1).equalsIgnoreCase("<")) {
                bot.keyPress(16);
                bot.delay(10);
                bot.keyPress(44);
                bot.delay(1);
                bot.keyRelease(44);
                bot.keyRelease(16);
            } else if (cmd.substring(i, i + 1).equalsIgnoreCase(">")) {
                bot.keyPress(16);
                bot.delay(10);
                bot.keyPress(46);
                bot.delay(1);
                bot.keyRelease(46);
                bot.keyRelease(16);
            } else if (cmd.length() - i >= 2 && cmd.substring(i, i + 2).equalsIgnoreCase("\\-")) { //change to something similar to the '/' if not using '-' as cmd prompter
                bot.keyPress(45);
                bot.delay(1);
                bot.keyRelease(45);
            } else if (cmd.substring(i, i + 1).equalsIgnoreCase(":")) {
                bot.keyPress(16);
                bot.delay(10);
                bot.keyPress(59);
                bot.delay(1);
                bot.keyRelease(59);
                bot.keyRelease(16);
            } else if (cmd.substring(i, i + 1).equalsIgnoreCase("*")) {
                bot.keyPress(16);
                bot.delay(10);
                bot.keyPress(56);
                bot.delay(1);
                bot.keyRelease(56);
                bot.keyRelease(16);
            } else if (cmd.substring(i, i + 1).equalsIgnoreCase("~")) {
                bot.keyPress(16);
                bot.delay(10);
                bot.keyPress(192);
                bot.delay(1);
                bot.keyRelease(192);
                bot.keyRelease(16);
            }
            bot.keyRelease(16);
            bot.delay(10);
        }
    }






    // methods from website https://howtodoinjava.com/java/library/java-aiml-chatbot-example/
    private static String getResourcesPath() {
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        path = path.substring(0, path.length() - 2);
        System.out.println(path);
        return path + File.separator + "src" + File.separator + "main" + File.separator + "resources";
    }

    public static String chatBot(String input) {
        if (input.equals("error"))
            return input;
        try {

            String resourcesPath = getResourcesPath();
            System.out.println(resourcesPath);
            MagicBooleans.trace_mode = TRACE_MODE;
            Bot bot = new Bot("super", resourcesPath);
            Chat chatSession = new Chat(bot);
            bot.brain.nodeStats();
            String textLine = input;
            if (textLine.length() < 1)
                textLine = MagicStrings.null_input;

            String request = textLine;
            if (MagicBooleans.trace_mode)
                System.out.println(
                        "STATE=" + request + ":THAT=" + ((History<?>) chatSession.thatHistory.get(0)).get(0)
                                + ":TOPIC=" + chatSession.predicates.get("topic"));
            String response = chatSession.multisentenceRespond(request);
            while (response.contains("&lt;"))
                response = response.replace("&lt;", "<");
            while (response.contains("&gt;"))
                response = response.replace("&gt;", ">");
            System.out.println("Robot : " + response);
            if (response.contains("<search>"))
                return "idk lol";
            return response;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }


}
