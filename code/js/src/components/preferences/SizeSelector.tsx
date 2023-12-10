export function SizeSelector({ sizeSelected, onClick }) {
  const sizes = [15, 19];

  const defaultColor =
    "bg-blue-500 hover:bg-blue-400 text-white font-bold py-2 px-4 border-b-4 border-blue-700 hover:border-blue-500";
  const selectedColor =
    "bg-blue-700 hover:bg-blue-600 text-white font-bold py-2 px-4 border-b-4 border-blue-900 hover:border-blue-700";

  return (
    <div className="inline-flex rounded-md shadow-sm" role="group">
      {sizes.map((it) => {
        return (
          <div key={it}>
            <button
              className={sizeSelected == it ? selectedColor : defaultColor}
              onClick={() => {
                onClick(it);
              }}
            >
              {it}
            </button>
          </div>
        );
      })}
    </div>
  );
}
