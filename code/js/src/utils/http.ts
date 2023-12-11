import { SirenEntity } from "../types/siren";

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

  const rsp = await fetch(path, options);
  const content = await rsp.json();

  if (!rsp.ok) throw content;

  return content;
}

export function requestBuilder(UriTemplate: string, args: Array<any>): string {
  let url = UriTemplate.split("/")
    .map((it) => {
      if (it.indexOf(":") != -1) return args.shift();
      return it;
    })
    .toString();
  while (url.indexOf(",") != -1) {
    url = url.replace(",", "/");
  }
  return url;
}

/* export function requestBuilder(UriTemplate: string, args: Record<string, any>): string {
  let urlParts = UriTemplate.split("/");
  let path = urlParts.map((it) => {
    if (it.indexOf(":") !== -1) {
      const paramName = it.slice(1); // Remove the ":" from the placeholder
      return args[paramName] !== undefined ? args[paramName] : it;
    } else {
      return it;
    }
  }).join('/'); // Join the array elements with '/'

  // Handle the case where there are still arguments (for query parameters)
  const queryParams = Object.keys(args)
    .filter((paramName) => !urlParts.includes(`:${paramName}`) && args[paramName] !== undefined)
    .map((paramName) => `${encodeURIComponent(paramName)}=${encodeURIComponent(args[paramName])}`)
    .join('&');

  if (queryParams) {
    path += path.includes('?') ? `&${queryParams}` : `?${queryParams}`;
  }

  let substringToRemove = "skip=:skip&limit=:limit&";

  if (path.includes(substringToRemove)) {
    path = path.replace(substringToRemove, "");
  }

  return path;
} */
