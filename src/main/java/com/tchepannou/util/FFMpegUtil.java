/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.util;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Utility for invokin <code>ffmeg</code> utility.
 * 
 * @author tcheer
 */
public class FFMpegUtil
{
    //-- Static methods
    private static final Pattern DURATION_LINE_PATTERN = Pattern.compile ("\\s{2}Duration:\\s\\d{2}:\\d{2}:\\d{2}");
    public static final Pattern DUR_PATTERN = Pattern.compile ("\\d{2}:\\d{2}:\\d{2}");
//    public static final Pattern DIM_PATTERN = Pattern.compile ("\\d+x\\d+");
    private static final String FFMPEG = "ffmpeg -i ";
    private static final String CMD_FFMPEG = FFMPEG + "<input_file>";
    private static final String CMD_FFMPEG_EXTRACT_THUMBNAIL = FFMPEG + "<input_file> -vframes 1 -ss <position> -s <width>x<height> -f image2 <output_file>";
    private static final String CMD_FFMPEG_CUT = FFMPEG + "<input_file> -sameq -t <duration>s <output_file>";
    private static final String CMD_FFMPEG_FLV2MOV = FFMPEG + "<input_file> <output_file>";


    //-- Public methods
    public static int extractThumbnailsFromVideo (String videoPath, String outputDir, int width, int height)
        throws IOException, InterruptedException
    {
        // extract thumbnail in the first 30 secs
        long seconds  = getVideoDurationSeconds (videoPath);
        long duration = Math.min (30, seconds);
        int  step = 5;
        if (duration == 0)
        {
            return 0;
        }

        File dir = new File (outputDir);
        for (int i=0 ; i<=duration ; i += step)
        {
            long position = i;
            String idx = i<10 ? "0" + i : String.valueOf(i);
            String imagePath = new File(dir, "out_" + idx + ".jpg").getAbsolutePath ();
            extractThumbnailFromVideo (videoPath, imagePath, width, height, position);
        }
        return 0;
    }

    public static int extractThumbnailFromVideo (String videoPath, String imagePath, int width, int height)
        throws IOException, InterruptedException
    {
        //Thumbnail extracted after 10% of the videoPath duration.
        long seconds = getVideoDurationSeconds (videoPath);
        long position = seconds > 0 ? seconds / 10 : 0;

        /* Execute the command */
        return extractThumbnailFromVideo (videoPath, imagePath, width, height, position);
    }

    public static int extractThumbnailFromVideo (String videoPath, String imagePath, int width, int height, long position)
        throws IOException, InterruptedException
    {
        /* Execute the command */
        String cmd = StringUtil.replace (CMD_FFMPEG_EXTRACT_THUMBNAIL, "<input_file>", videoPath);
        cmd = StringUtil.replace (cmd, "<position>", "" + ( int ) position);
        cmd = StringUtil.replace (cmd, "<height>", "" + fixDimension (height));
        cmd = StringUtil.replace (cmd, "<width>", "" + fixDimension (width));
        cmd = StringUtil.replace (cmd, "<output_file>", imagePath);
        CommandLine cmdLine = new CommandLine (cmd);
        int result = cmdLine.exec ();
        return result;
    }

    public static String getVideoDurationText (String videoPath)
        throws IOException, InterruptedException
    {
        String cmd = StringUtil.replace (CMD_FFMPEG, "<input_file>", videoPath);
        
        /* Exec ffmpeg */
        CommandLine cl = new CommandLine (cmd);
        cl.exec ();

        /* Extract duration */
        String stdErr = cl.getStdErr ();
        return extractDuration (stdErr);
    }

    public static int getVideoDurationSeconds (String videoPath)
        throws IOException, InterruptedException
    {
        String duration = getVideoDurationText (videoPath);
        if ( duration == null )
        {
            return -1;
        }
        else
        {
            String[] s = duration.split (":");
            int hh = Integer.parseInt (s[0]);
            int mm = Integer.parseInt (s[1]);
            int ss = Integer.parseInt (s[2]);

            return hh * 3600 + mm * 60 + ss;
        }
    }
    
    public static int cut(String videoPath, String outputPath, int durationSeconds)
        throws IOException, InterruptedException
    {
        //String CMD_FFMPEG_CUT = FFMPEG + "<input_file> -sameq -t <duration>s <output_file>";
        String cmd = StringUtil.replace (CMD_FFMPEG_CUT, "<input_file>", videoPath);
        cmd = StringUtil.replace (cmd, "<duration>", "" + durationSeconds);
        cmd = StringUtil.replace (cmd, "<output_file>", outputPath);
        CommandLine cmdLine = new CommandLine (cmd);
        int result = cmdLine.exec ();
        return result;        
    }

    public static int flv2mov(String flv, String mov)
        throws IOException, InterruptedException
    {
        String cmd = StringUtil.replace (CMD_FFMPEG_FLV2MOV, "<input_file>", flv);
        cmd = StringUtil.replace (cmd, "<output_file>", mov);
        CommandLine cmdLine = new CommandLine (cmd);
        int result = cmdLine.exec ();
        return result;        
    }

    //-- Private methods
    private static int fixDimension (int dim)
    {
        // This prevent a ffmpeg bug that fails when dimension are not
        // multiple of 2
        return (int)Math.floor ((dim/2)*2);
    }
    private static String extractDuration (String s)
    {
        Matcher matcher = DURATION_LINE_PATTERN.matcher (s);
        if ( matcher.find () )
        {
            String line = matcher.group ();
            Scanner scanner = new Scanner (line);
            return scanner.findInLine (DUR_PATTERN);
        }
        return null;
    }
}