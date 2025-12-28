export function toBase64(file: Blob): Promise<string> {
  return new Promise<string>((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => resolve(String(reader.result));
    reader.onerror = error => reject(error);
  });
}

export function getSeverityByStockStatusId(id: number): string {
  switch (id) {
    case 1:
      return 'success'
    case 2:
      return 'warning'
    case 3:
      return 'danger'
  }
  return 'info'
}
