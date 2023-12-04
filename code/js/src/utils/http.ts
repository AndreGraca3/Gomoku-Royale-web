import { SirenEntity } from "../types/siren";

const BASE_URL = "/api";

/**
 * Makes an HTTP request to the specified path with the given method, body, and authentication token.
 *
 * @param {string} path - The path of the API endpoint.
 * @param {string} method - The HTTP method for the request (e.g., "GET", "POST", "PUT", "DELETE").
 * @param {object} body - The request body to send (optional).
 * @returns {Promise<any>} - A promise that resolves to the parsed JSON response.
 * @throws {any} - Throws an error if the response is not successful.
 */
export async function fetchAPI<T>(
  path: string,
  method: string = "GET",
  body?: Object
): Promise<SirenEntity<T>> {
  const headers = {
    "Content-Type": "application/json",
  };

  const options = {
    method,
    headers,
  };

  if (body) options["body"] = JSON.stringify(body);

  const rsp = await fetch(BASE_URL + path, options);
  const content = await rsp.json();

  if (!rsp.ok) throw content;

  return content;
}
