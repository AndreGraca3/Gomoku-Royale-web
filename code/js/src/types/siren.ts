export type SirenEntity<T> = {
  class?: string[];
  properties?: T;
  entities?: SubEntity[];
  links?: SirenLink[];
  actions?: SirenAction[];
  title?: string;
};

export type SubEntity = {
  class?: string[];
  rel?: string[];
  href?: any;
  type?: string;
  title?: string;
};

export type SirenLink = {
  rel: string[];
  class?: string[];
  href: string;
  title?: string;
  type?: string;
};

export type SirenAction = {
  name: string;
  class?: string[];
  method?: string;
  href: string;
  title?: string;
  type?: string;
  fields?: SirenField[];
};

export type SirenField = {
  name: string;
  type?: string;
  value?: string;
  title?: string;
};
