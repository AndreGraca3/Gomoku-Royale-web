export function SizeSelector({ sizeSelected, onClick }) {
  const sizes = [15, 19];

  const defaultColor =
    "bg-blue-500 hover:bg-blue-600 border-blue-400 hover:border-blue-700";
  const selectedColor =
    "scale-105 bg-blue-700 hover:bg-blue-600 font-bold border-blue-900 hover:border-blue-700";

  return (
    <div className="inline-flex rounded-md shadow-sm" role="group">
      {sizes.map((size) => {
        return (
          <div key={size}>
            <button
              className={`py-2 px-4 border-b-4 transition-all duration-100 ${size == sizeSelected ? selectedColor : defaultColor }`}
              onClick={() => {
                onClick(size);
              }}
            >
              {size}
            </button>
          </div>
        );
      })}
    </div>
  );
}
