package org.sonatype.goodies.grafeas.testsuite

import javax.ws.rs.core.Response

import org.apache.http.HttpHeaders

/**
 * JAX-RS {@link Response} assert helpers.
 */
class ResponseAssert
{
  static assertStatus(final Response response, final Response.Status status) {
    assert response.status == status.statusCode
  }

  static assertContentType(final Response response, final String mediaType) {
    assert response.getHeaderString(HttpHeaders.CONTENT_TYPE) == mediaType
  }
}
