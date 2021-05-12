import com.pty4j.PtyProcess;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

public class Terminal {
    // The command to run in a PTY...
    String[] cmd = { "/bin/sh", "-l" };
    // The initial environment to pass to the PTY child process...
    String[] env = { "TERM=xterm" };

    PtyProcess pty = PtyProcess.exec(cmd, env);

    OutputStream os = pty.getOutputStream();
    InputStream is = pty.getInputStream();


// ... work with the streams ...

    // wait until the PTY child process terminates...
    int result = pty.waitFor();

    public Terminal() throws IOException, InterruptedException {
        System.out.println(os);
        System.out.println(is);
        System.out.println(pty);
        System.out.println(Arrays.toString(env));
    }
}
