import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Terminal {
    String filename = Main.class.getProtectionDomain().getCodeSource().getLocation().toString().substring(6);
    // The command to run in a PTY...
    String[] cmd = {"cmd","/c","start","cmd","/k","java -jar \"" + filename + "\""};
    // The initial environment to pass to the PTY child process...
    String[] env = { "TERM=xterm" };

    Process pty = Runtime.getRuntime().exec(cmd, env);

    OutputStream os = pty.getOutputStream();
    InputStream is = pty.getInputStream();


// ... work with the streams ...

    // wait until the PTY child process terminates...
    int result = pty.waitFor();

    public Terminal() throws IOException, InterruptedException {

    }
}
