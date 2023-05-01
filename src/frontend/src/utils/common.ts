const CUTTING_EXPRESSION = /\s+[^\s]*$/;
export const createShortcut = (text: string, limit: number) => {
    if (text.length > limit) {
      const part = text.slice(0, limit - 3);
      if (part.match(CUTTING_EXPRESSION)) {
        return part.replace(CUTTING_EXPRESSION, " ...");
      }
      return part + "...";
    }
    return text;
  };

  
export const removeTags = (str:string) => {
  if ((str===null) || (str===''))
      return false;
  else
      str = str.toString();
  return str.replace( /(<([^>]+)>)/ig, '');
}