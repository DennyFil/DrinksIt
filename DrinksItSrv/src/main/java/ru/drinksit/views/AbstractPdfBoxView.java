package ru.drinksit.views;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.cert.CRLException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.web.servlet.view.AbstractView;

/**
 * Abstract superclass for PDF views, using
 * <a href="https://pdfbox.apache.org/">PDFBox</a> library.
 * Application-specific view classes will extend this class.
 * The view will be held in the subclass itself, not in a template.
 *
 * <p>Note: Internet Explorer requires a ".pdf" extension, as
 * it doesn't always respect the declared content type.
 *
 * @author Jorge Davison
 * @see org.springframework.web.servlet.view.document.AbstractPdfStamperView
 */
public abstract class AbstractPdfBoxView extends AbstractView {

  /**
   * This constructor sets the appropriate content type "application/pdf".
   * Note that IE won't take much notice of this, but there's not a lot we
   * can do about this. Generated documents should have a ".pdf" extension.
   */
  public AbstractPdfBoxView() {
    setContentType("application/pdf");
  }

  @Override
  protected boolean generatesDownloadContent() {
    return true;
  }

  @Override
  protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {

    // IE workaround: write into byte array first.
    ByteArrayOutputStream baos = createTemporaryOutputStream();

    // Apply preferences and build metadata.
    PDDocument document = newDocument();

    // Build PDF document.
    buildPdfDocument(model, document, request, response);
    document.save(baos);
    document.close();

    // Flush to HTTP response.
    writeToResponse(response, baos);
  }

  /**
   * Create a new document to hold the PDF contents.
   * @return the newly created PDFBox Document instance
   * @see org.apache.pdfbox.pdmodel.PDDocument#PDDocument()
   */
  protected PDDocument newDocument() {
    return new PDDocument();
  }

  /**
   * Subclasses must implement this method to build an PDFBox PDF document,
   * given the model.
   * <p>Note that the passed-in HTTP response is just supposed to be used
   * for setting cookies or other HTTP headers. The built PDF document itself
   * will automatically get written to the response after this method returns.
   * @param model the model Map
   * @param document the PDFBox Document to add elements to
   * @param request in case we need locale etc. Shouldn't look at attributes.
   * @param response in case we need to set cookies. Shouldn't write to it.
   * @throws Exception any exception that occurred during document building
   */
  protected abstract void buildPdfDocument(Map<String, Object> model, PDDocument document,
                                           HttpServletRequest request, HttpServletResponse response)
      throws IOException, SignatureException, CRLException, NoSuchAlgorithmException;
}
