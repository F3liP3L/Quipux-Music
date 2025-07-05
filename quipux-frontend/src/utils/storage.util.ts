
export class StorageUtil {
  
  static isAvailable(): boolean {
    return typeof window !== 'undefined' && window.localStorage !== undefined;
  }

  static getItem(key: string): string | null {
    if (this.isAvailable()) {
      return localStorage.getItem(key);
    }
    return null;
  }

  static setItem(key: string, value: string): void {
    if (this.isAvailable()) {
      localStorage.setItem(key, value);
    }
  }

  static removeItem(key: string): void {
    if (this.isAvailable()) {
      localStorage.removeItem(key);
    }
  }

  static clear(): void {
    if (this.isAvailable()) {
      localStorage.clear();
    }
  }
} 