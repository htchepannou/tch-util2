/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * Utility for image manipulation
 * 
 * @author herve
 */
public class ImageUtil
{
    //-- Static Attributes
    private static final int JPG_QUALITY = 100;
    public static final String JPG = "jpg";
    public static final String PNG = "png";
    public static final String GIF = "gif";
    public static final String RESIZE_POLICY_ALWAYS = "always";
    public static final String RESIZE_POLICY_NEVER = "never";
    public static final String RESIZE_POLICY_SCALE_UP = "scale_up";
    public static final String RESIZE_POLICY_SCALE_DOWN = "scale_down";

    //-- Methods
    /**
     * Return the dimension of an image
     *
     * @param img Image
     * @return Dimension of the image
     *
     * @throws java.io.IOException if any IO error occurs
     */
    public static Dimension dimension (InputStream img)
        throws IOException
    {
        InputStream imageStream = new BufferedInputStream (img);
        Image image = ( Image ) ImageIO.read (imageStream);
        if ( image != null )
        {
            int w = image.getWidth (null);
            int h = image.getHeight (null);
            return new Dimension (w, h);
        }
        else
        {
            return null;
        }
    }

    /**
     * Resize an image and store it as <code>JPG</code>, return the resized stream or
     * <code>null</code> if unable to resize
     * See http://www.webmaster-talk.com/coding-forum/63227-image-resizing-in-java.html
     *
     * @param in           Image to resize
     * @param width        Target width
     * @param height       Target height
     * @param targetType   Target image type
     * @param resizePolicy Policy for resizing. If not provided, the resizing will be performed.
     *          Here are the possible values:
     *          <ul>
     *              <li><code>always</code>: Always resize the image</li>
     *              <li><code>never</code>: Never resize the image</li>
     *              <li><code>scale_down</code>: Resize only in order to reduce the image</li>
     *              <li><code>scale_up</code>: Resize only in order to enlarge the image</li>
     *          </ul>
     *
     * @return InputStream resized, or <code>null</code> if the resizing fails.
     * 
     * @throws java.io.IOException if any IO error occurs
     */
    public static InputStream resize (InputStream in, int width, int height, String targetType, String resizePolicy)
        throws IOException
    {
        return resize (in, width, height, targetType, resizePolicy, true);
    }

    public static InputStream resize (InputStream in, int width, int height, String targetType, String resizePolicy, boolean maintainRatio)
        throws IOException
    {
        //InputStream imageStream = new BufferedInputStream (in);
        Image image = ( Image ) ImageIO.read (in);
        if ( image != null )
        {
            int thumbWidth = width, imageWidth = width;
            int thumbHeight = height, imageHeight = height;

            // Make sure the aspect ratio is maintained, so the image is not skewed
            if ( maintainRatio )
            {
                double thumbRatio = ( double ) thumbWidth / ( double ) thumbHeight;
                imageWidth = image.getWidth (null);
                imageHeight = image.getHeight (null);
                double imageRatio = ( double ) imageWidth / ( double ) imageHeight;
                if ( thumbRatio < imageRatio )
                {
                    thumbHeight = ( int ) (thumbWidth / imageRatio);
                }
                else
                {
                    thumbWidth = ( int ) (thumbHeight * imageRatio);
                }
            }

            // Make sure that we can resize
            if ( !canResize (imageWidth, imageHeight, thumbWidth, thumbHeight, resizePolicy) )
            {
                return null;
            }

            return resize (image, thumbWidth, thumbHeight, targetType);
        }
        else
        {
            return null;
        }
    }

    private static InputStream resize (Image image, int thumbWidth, int thumbHeight, String targetType)
        throws IOException
    {
        BufferedImage originalImage = toBufferedImage(image);
        BufferedImage resizedImage = new BufferedImage(thumbWidth, thumbHeight, originalImage.getType());
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, thumbWidth, thumbHeight, null);
        g.dispose();
        g.setComposite(AlphaComposite.Src);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

        ByteArrayOutputStream out = new ByteArrayOutputStream ();
        ImageIO.write(resizedImage, "png", out);

        ByteArrayInputStream bis = new ByteArrayInputStream (out.toByteArray ());
        return bis;

//        // Draw the scaled image
//        BufferedImage thumbImage = new BufferedImage (thumbWidth, thumbHeight, BufferedImage.TYPE_INT_RGB);
//        Graphics2D graphics2D = thumbImage.createGraphics ();
//        graphics2D.setRenderingHint (RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
//        graphics2D.drawImage (image, 0, 0, thumbWidth, thumbHeight, null);
//
//        // Write the scaled image to the outputstream
//        ByteArrayOutputStream out = new ByteArrayOutputStream ();
//        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder (out);
//        JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam (thumbImage);
//        int quality = JPG_QUALITY; // Use between 1 and 100, with 100 being highest quality
//        quality = Math.max (0, Math.min (quality, JPG_QUALITY));
//        param.setQuality (( float ) quality / ( float ) JPG_QUALITY, false);
//        encoder.setJPEGEncodeParam (param);
//        encoder.encode (thumbImage);
//        ImageIO.write (thumbImage, targetType, out);
//
//        // Read the outputstream into the inputstream for the return value
//        ByteArrayInputStream bis = new ByteArrayInputStream (out.toByteArray ());
//        return bis;
    }

    private static BufferedImage toBufferedImage(Image img)
    {
        if (img instanceof BufferedImage)
        {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }
    public static boolean canResize (int width, int height, int targetWidth, int targetHeigh, String resizePolicy)
    {
        if ( RESIZE_POLICY_NEVER.equals (resizePolicy) )
        {
            return false;
        }
        else if ( resizePolicy == null || RESIZE_POLICY_ALWAYS.equals (resizePolicy) )
        {
            return true;
        }
        else
        {
            /* The image dimension is smaller than the target dimension ... */
            if ( width < targetWidth && height < targetHeigh )
            {
                /* resize if the policy is SCALE_UP */
                return RESIZE_POLICY_SCALE_UP.equals (resizePolicy);
            }

            /* The image dimension is bigger than the target dimension ... */
            else
            {
                /* resize if the policy is SCALE_DOWN */
                return RESIZE_POLICY_SCALE_DOWN.equals (resizePolicy);
            }
        }
    }

}